package ar.unrn.tp.dto;

import ar.unrn.tp.dto.MarcaDTO;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;

import java.io.Serializable;

/**
 * DTO for {@link Producto}
 */
public record ProductoDTO(Long id, String codigo, String descripcion, Categoria categoria, MarcaDTO marca,
                          double precio) implements Serializable {
    @Override
    public String toString() {
        return "Producto " + codigo + " - " + descripcion + " - Precio: $"+ precio +" - " +  marca.nombre() + " - " + categoria.name() ;
    }
}