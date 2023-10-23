package ar.unrn.tp.main;

import ar.unrn.tp.config.LocalDateTypeAdapter;
import ar.unrn.tp.jpa.servicios.ServiceJPAImpl;
import ar.unrn.tp.modelo.Venta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManagerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.json.DefaultGsonObjectMapper;
import redis.clients.jedis.json.JsonObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaRedisServiceJPAImpl extends ServiceJPAImpl implements VentaRedisService {
    private Jedis jedis;
    public VentaRedisServiceJPAImpl(EntityManagerFactory emf, Jedis jedis) {
        super(emf);
        this.jedis = jedis;
    }
    @Override
    public List<Venta> ultimas3Ventas(Long idCliente) {
        List<Venta> listaVentas = new ArrayList<>();
        List<String> ventas = jedis.lrange("ventas#"+idCliente, 0, 2);
        if (!ventas.isEmpty()) {
            for (String venta : ventas) {
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter()).create();
                Venta v = gson.fromJson(venta, Venta.class);
                listaVentas.add(v);
            }
        } else {
            inTransactionExecute((em) -> {
                listaVentas.addAll(em.createQuery("select v from Venta v order by v.fechaHora desc", Venta.class).setMaxResults(3).getResultList());
            });
            cargarVentasARedis(listaVentas, idCliente);
        }
        return listaVentas;
    }

    @Override
    public void actualizarVentas(Long idCliente) {
        List<Venta> listaVentas = new ArrayList<>();
        inTransactionExecute((em) -> {
            listaVentas.addAll(em.createQuery("select v from Venta v order by v.fechaHora desc", Venta.class).setMaxResults(3).getResultList());
        });
        jedis.del("ventas#"+idCliente);
        cargarVentasARedis(listaVentas, idCliente);
    }

    private void cargarVentasARedis(List<Venta> listaVentas, Long idCliente) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter()).create();
        for (Venta venta : listaVentas) {
            jedis.lpush("ventas#"+idCliente, gson.toJson(venta));
        }
    }
}
