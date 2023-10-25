package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.Categoria;

import java.io.Serializable;

/**
 * DTO for {@link ar.unrn.tp.modelo.ProductoHistorico}
 */
public record ProductoHistoricoDTO(Long id, String codigo, String descripcion, Categoria categoria, MarcaDTO marca,
                                   double precio) implements Serializable {
}