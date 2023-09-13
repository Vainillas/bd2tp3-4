package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.modelo.EmisorTarjeta;
import ar.unrn.tp.modelo.Promocion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class PromocionServiceTest {
    private EntityManagerFactory emf;
    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
    }
    @AfterEach
    public void tearDown() {
        emf.close();
    }
    @Test
    public void persistirPromocion() {
        PromocionService promocionService = new PromocionServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        marcaService.crearMarca("Marca1");
        promocionService.crearDescuento(1L, "Marca1", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.1);
        Promocion promo1 = promocionService.encontrarPromocionProducto(1L);
        Assertions.assertEquals(0.1, promo1.getDescuento());
    }
    @Test
    public void persistirPromocionTarjeta() {
        PromocionService promocionService = new PromocionServiceJPAImpl(emf);
        promocionService.crearDescuentoSobreTotal(1L, String.valueOf(EmisorTarjeta.PATAGONIA), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.2);
        Promocion promo1 = promocionService.encontrarPromocionCompra(1L);
        Assertions.assertEquals(0.2, promo1.getDescuento());
    }

}
