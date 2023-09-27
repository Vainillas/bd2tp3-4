package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.api.exceptions.ServiceException;
import ar.unrn.tp.modelo.*;
import ar.unrn.tp.modelo.exceptions.BusinessException;
import jakarta.persistence.EntityManagerFactory;


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
            if(venta[0] == null) throw new BusinessException("No existe la venta");
        });
        return venta[0];
    }

    @Override
    public void realizarVenta(Long idVenta, Long idCliente, List<Long> productos, Long idTarjeta) {
        try{
        inTransactionExecute((em) -> { //TODO: refactor exceptions
            Cliente c = em.find(Cliente.class, idCliente);
            if(c == null) throw new BusinessException("El cliente no existe");
            TarjetaCredito t = em.find(TarjetaCredito.class, idTarjeta);
            if(t == null || !c.getTarjetas().contains(t)) throw new BusinessException("La tarjeta no existe o no pertenece al cliente");
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            if(productosVenta.isEmpty()) throw new BusinessException("La lista de productos no puede estar vacía");
            Venta v = new Venta(idVenta, LocalDateTime.now(), c, productosVenta, calcularMonto(productos, idTarjeta));
            t.descontarFondos(v.getMontoTotal());
            em.merge(t);
            em.persist(v);
        });}
        catch (Exception e){
            throw new ServiceException("Error al realizar la venta: "+ e.getMessage());
        }
    }
    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        try{
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            if(c == null) throw new BusinessException("El cliente no existe");
            TarjetaCredito t = em.find(TarjetaCredito.class, idTarjeta);
            if(t == null || !c.getTarjetas().contains(t)) throw new BusinessException("La tarjeta no existe o no pertenece al cliente");
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            if(productosVenta.isEmpty()) throw new BusinessException("La lista de productos no puede estar vacía");
            Venta v = new Venta(LocalDateTime.now(), c, productosVenta, calcularMonto(productos, idTarjeta));
            t.descontarFondos(v.getMontoTotal());
            em.merge(t);
            em.persist(v);
        });}
        catch (Exception e){
            throw new ServiceException("Error al realizar la venta: "+ e.getMessage());
        }
    }

    @Override
    public void realizarVenta(Long idVenta, Long idCliente, List<Long> productos, String numeroTarjeta) {
        try{
            inTransactionExecute((em) -> {
                Cliente c = em.find(Cliente.class, idCliente);
                if(c == null) throw new BusinessException("El cliente no existe");
                TarjetaCredito t = em.find(TarjetaCredito.class, numeroTarjeta);
                if(t == null || !c.getTarjetas().contains(t)) throw new BusinessException("La tarjeta no existe o no pertenece al cliente");
                List<Producto> productosVenta = productoService.encontrarProductos(productos);
                if(productosVenta.isEmpty()) throw new BusinessException("La lista de productos no puede estar vacía");
                Venta v = new Venta(idVenta, LocalDateTime.now(), c, productosVenta, calcularMonto(productos, numeroTarjeta));
                t.descontarFondos(v.getMontoTotal());
                em.merge(t);
                em.persist(v);
            });
        }catch (Exception e){
            throw new ServiceException("Error al realizar la venta: "+ e.getMessage());
        }

    }

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, String numeroTarjeta) {
        try{
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            if(c == null) throw new BusinessException("El cliente no existe");
            TarjetaCredito t = em.find(TarjetaCredito.class, numeroTarjeta);
            if(t == null || !c.getTarjetas().contains(t)) throw new BusinessException("La tarjeta no existe o no pertenece al cliente");
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            if(productosVenta.isEmpty()) throw new BusinessException("La lista de productos no puede estar vacía");
            Venta v = new Venta(LocalDateTime.now(), c, productosVenta, calcularMonto(productos, numeroTarjeta));
            t.descontarFondos(v.getMontoTotal());

            em.merge(t);
            em.persist(v);
        });}
        catch (Exception e){
            throw new ServiceException("Error al realizar la venta: "+ e.getMessage());
        }
    }

    @Override
    public double calcularMonto(List<Long> productos, Long idTarjeta) {
        AtomicReference<Double> monto = new AtomicReference<>((double) 0);
        inTransactionExecute((em) -> {
            TarjetaCredito tarjetaCredito = em.find(TarjetaCredito.class, idTarjeta);
            if(tarjetaCredito == null) throw new BusinessException("La tarjeta no existe");
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            if(productosVenta.isEmpty()) throw new BusinessException("La lista de productos no puede estar vacía");
            PromocionCollector promociones = recuperarPromocionesActivas();
            CarritoCompra carrito = new CarritoCompra(productosVenta, tarjetaCredito, promociones);
            monto.set(carrito.calcularTotal(tarjetaCredito));
        });
        return monto.get();
    }
    @Override
    public double calcularMonto(List<Long> productos, String numeroTarjeta) {
        AtomicReference<Double> monto = new AtomicReference<>((double) 0);
        inTransactionExecute((em) -> {
            TarjetaCredito tarjetaCredito = em.find(TarjetaCredito.class, numeroTarjeta);
            if(tarjetaCredito == null) throw new BusinessException("La tarjeta no existe");
            List<Producto> productosVenta = productoService.encontrarProductos(productos);
            if(productosVenta.isEmpty()) throw new BusinessException("La lista de productos no puede estar vacía");
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
