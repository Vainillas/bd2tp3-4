package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.Marca;

import java.io.Serializable;

/**
 * DTO for {@link Marca}
 */
public record MarcaDTO(String nombre) implements Serializable {
}