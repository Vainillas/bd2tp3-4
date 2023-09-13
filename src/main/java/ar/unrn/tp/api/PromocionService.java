package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.PromocionCompra;
import ar.unrn.tp.modelo.PromocionProducto;

import java.time.LocalDate;
import java.util.List;

public interface PromocionService {
    PromocionCompra encontrarPromocionCompra(Long idPromocion);
    PromocionProducto encontrarPromocionProducto(Long idPromocion);

    List<Promocion> encontrarPromociones();
    // validar que las fechas no se superpongan
    void crearDescuentoSobreTotal(Long ID, String marcaTarjeta, LocalDate fechaDesde,
                                  LocalDate fechaHasta, double porcentaje);

    // validar que las fechas no se superpongan
    void crearDescuento(Long ID,String marcaProducto, LocalDate fechaDesde, LocalDate
            fechaHasta, double porcentaje);
}
