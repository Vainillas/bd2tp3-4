package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.EmisorTarjeta;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ar.unrn.tp.modelo.PromocionCompra}
 */
public record PromocionCompraDTO(Long id, double DESCUENTO, LocalDate diaInicio, LocalDate diaFin,
                                 EmisorTarjeta emisorTarjeta) implements Serializable {
}