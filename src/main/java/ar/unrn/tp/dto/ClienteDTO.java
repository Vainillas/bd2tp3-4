package ar.unrn.tp.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ar.unrn.tp.modelo.Cliente}
 */
public record ClienteDTO(String dni, Long id, String nombre, String apellido, String email,
                         List<TarjetaDTO> tarjetas) implements Serializable {
}