package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class VentaServiceJPAImpl extends ServiceJPAImpl implements VentaService {
    ProductoService productoService;
    PromocionService promocionService;
    public VentaServiceJPAImpl(EntityManagerFactory entityManagerFactory, ProductoService productoService, PromocionService promocionService){
        super(entityManagerFactory);
        this.productoService = productoService;
        this.promocionService = promocionService;
    }

    @Override
    public Venta encontrarVenta(Long idVenta) {
        Venta[] venta = new Venta[1];
        inTransactionExecute((em) -> {
            venta[0] = em.find(Venta.class, idVenta);
        });
        return venta[0];
    }

    @Override
    public void realizarVenta(Long idVenta, Long idCliente, List<Long> productos, Long idTarjeta) {
        inTransactionExecute((em) -> { //TODO: refactor exceptions
            Cliente c = em.find(Cliente.class, idCliente);
            if(c == null) throw new RuntimeException("El cliente no existe");
            TarjetaCredito t = em.find(TarjetaCredito.class, idTarjeta);
            if(t == null || !c.getTarjetas().contains(t)) throw new RuntimeException("La tarjeta no existe o no pertenece al cliente");
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            if(productosVenta.isEmpty()) throw new RuntimeException("La lista de productos no puede estar vacía");
            Venta v = new Venta(idVenta, LocalDateTime.now(), c, productosVenta, calcularMonto(productos, idTarjeta));
            em.persist(v);
        });
    }
    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            TarjetaCredito t = em.find(TarjetaCredito.class, idTarjeta);
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            Venta v = new Venta(LocalDateTime.now(), c, productosVenta, calcularMonto(productos, idTarjeta));
            em.persist(v);
        });
    }

    @Override
    public double calcularMonto(List<Long> productos, Long idTarjeta) {
        AtomicReference<Double> monto = new AtomicReference<>((double) 0);
        inTransactionExecute((em) -> {
            TarjetaCredito tarjetaCredito = em.find(TarjetaCredito.class, idTarjeta);
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            PromocionCollector promociones = recuperarPromocionesActivas();
            CarritoCompra carrito = new CarritoCompra(productosVenta, tarjetaCredito, promociones);
            monto.set(carrito.calcularTotal(tarjetaCredito));
        });
        return monto.get();
    }
    private PromocionCollector recuperarPromociones() {
        PromocionCollector promociones = new PromocionCollector();
        inTransactionExecute((em) -> {
            promociones.agregarPromociones(promocionService.encontrarPromociones());
        });
        return promociones;
    }
    private PromocionCollector recuperarPromocionesActivas() {
        PromocionCollector promociones = new PromocionCollector();
        inTransactionExecute((em) -> {
            promocionService.encontrarPromociones().stream().filter(Promocion::estaActivaHoy).forEach(promociones::agregarPromocion);
        });
        return promociones;
    }

    @Override
    public List<Venta> ventas() {
        List<Venta> ventas = new ArrayList<>();
        inTransactionExecute((em) -> {
            ventas.addAll(em.createQuery("select v from Venta v", Venta.class).getResultList());
        });
        return ventas;
    }
}
