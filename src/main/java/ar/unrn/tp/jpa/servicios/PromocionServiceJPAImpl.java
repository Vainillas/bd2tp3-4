package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.modelo.*;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromocionServiceJPAImpl extends ServiceJPAImpl implements PromocionService {

    public PromocionServiceJPAImpl(EntityManagerFactory entityManager) {
        super(entityManager);
    }

    @Override
    public PromocionCompra encontrarPromocionCompra(Long idPromocion) {
        PromocionCompra[] promocion = new PromocionCompra[1];
        inTransactionExecute((em) -> {
            promocion[0] = em.find(PromocionCompra.class, idPromocion);
        });
        return promocion[0];
    }

    @Override
    public PromocionProducto encontrarPromocionProducto(Long idPromocion) {
        PromocionProducto[] promocion = new PromocionProducto[1];
        inTransactionExecute((em) -> {
            promocion[0] = em.find(PromocionProducto.class, idPromocion);
        });
        return promocion[0];
    }

    @Override
    public List<Promocion> encontrarPromociones() {
        List<Promocion> promociones = new ArrayList<>();
        inTransactionExecute((em) -> {
            promociones.addAll(em.createQuery("select p from PromocionCompra p", Promocion.class).getResultList());
            promociones.addAll(em.createQuery("select p from PromocionProducto p", Promocion.class).getResultList());
        });
        return promociones;
    }

    @Override
    public void crearDescuentoSobreTotal(Long ID, String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
        inTransactionExecute((em) -> {
            Promocion promocion = new PromocionCompra(ID, fechaDesde, fechaHasta, EmisorTarjeta.valueOf(marcaTarjeta), porcentaje);
            em.persist(promocion);
        });
    }

    @Override
    public void crearDescuento(Long ID, String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, double porcentaje) {
        inTransactionExecute((em) -> {
            Marca marca = em.find(Marca.class, marcaProducto);
            Promocion promocion = new PromocionProducto(ID, fechaDesde, fechaHasta, marca, porcentaje);
            em.persist(promocion);
        });
    }
}
