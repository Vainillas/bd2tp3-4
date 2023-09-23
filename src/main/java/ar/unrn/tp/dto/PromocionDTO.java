package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.EmisorTarjeta;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

public record PromocionDTO(String descripcion) implements Serializable {
}
