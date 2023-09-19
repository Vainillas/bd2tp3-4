package ar.unrn.tp.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ar.unrn.tp.modelo.PromocionProducto}
 */
public record PromocionProductoDTO(Long id, double DESCUENTO, LocalDate diaInicio, LocalDate diaFin,
                                   MarcaDTO marca) implements Serializable {
}