package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaServiceTest {
    private EntityManagerFactory emf;
    @AfterEach
    public void tearDown() {
        emf.close();
    }
    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
        ServiceJPAImpl serviceJPA = new ServiceJPAImpl(emf);
        serviceJPA.inTransactionExecute(
                (em) -> {
                    Cliente yo = new Cliente(1L, "Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
                    TarjetaCredito tarjetaVisa = new TarjetaCredito(1L, "123456789", true, 1000.0, EmisorTarjeta.VISA);
                    yo.agregarTarjeta(tarjetaVisa);
                    Marca dc = new Marca("DC Comics");
                    Producto batman = new Producto(1L, "DC-3", "Juguete de Batman", Categoria.JUGUETES, dc, 1000.0);
                    Producto superman = new Producto(2L, "DC-4", "Juguete de Superman", Categoria.JUGUETES, dc, 1000.0);
                    PromocionProducto promocionProducto = new PromocionProducto(1L, LocalDate.now().minusDays(5),LocalDate.now().plusDays(5), dc, 0.05);
                    PromocionCompra promocionCompra = new PromocionCompra(1L, LocalDate.now().minusDays(5),LocalDate.now().plusDays(5), EmisorTarjeta.VISA,0.1);

                    em.persist(yo);
                    em.persist(tarjetaVisa);
                    em.persist(dc);
                    em.persist(batman);
                    em.persist(superman);
                    em.persist(promocionProducto);
                    em.persist(promocionCompra);
                }
        );}

    @Test
    public void crearYPersistirVenta(){
        List<Long> productosId = new ArrayList<>();
        ServiceJPAImpl serviceJPA = new ServiceJPAImpl(emf);
        serviceJPA.inTransactionExecute((entityManager -> {
            productosId.addAll(entityManager.createQuery("select p.id from Producto p", Long.class).getResultList());
        }));
        VentaService ventaServiceJPA = new VentaServiceJPAImpl(emf, new ProductoServiceJPAImpl(emf), new PromocionServiceJPAImpl(emf));
        ventaServiceJPA.realizarVenta(1L, 1L, productosId, 1L);
        Venta venta = ventaServiceJPA.encontrarVenta(1L);
        Assertions.assertEquals(2, venta.getListaProductos().size());
        Assertions.assertEquals(1800.0, venta.getMontoTotal());
    }
    @Test
    public void crearVentaSinProductos(){
        VentaService ventaServiceJPA = new VentaServiceJPAImpl(emf, new ProductoServiceJPAImpl(emf), new PromocionServiceJPAImpl(emf));
        Assertions.assertThrows(RuntimeException.class, () -> {
            ventaServiceJPA.realizarVenta(1L, 1L, new ArrayList<>(), 1L);
        });
    }
    @Test
    public void crearVentaConTarjetaDeOtroCliente(){
        TarjetaCredito tarjetaMemeCard = new TarjetaCredito(2L, "123456710", true, 1000.0, EmisorTarjeta.MEMECARD);
        ServiceJPAImpl serviceJPA = new ServiceJPAImpl(emf);
        serviceJPA.inTransactionExecute((entityManager -> {
            entityManager.persist(tarjetaMemeCard);
        }));
        VentaService ventaServiceJPA = new VentaServiceJPAImpl(emf, new ProductoServiceJPAImpl(emf), new PromocionServiceJPAImpl(emf));
        List<Long> productosId = new ArrayList<>();
        serviceJPA.inTransactionExecute((entityManager -> {
            productosId.addAll(entityManager.createQuery("select p.id from Producto p", Long.class).getResultList());
        }));
        Assertions.assertThrows(RuntimeException.class, () -> {
            ventaServiceJPA.realizarVenta(1L, 1L, productosId, 2L);
        });
    }

    //TODO: me hacen falta más tests pero me quedé sin tiempo para la entrega.


}
