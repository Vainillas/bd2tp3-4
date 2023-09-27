package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.EmisorTarjeta;
import ar.unrn.tp.modelo.TarjetaCredito;

import java.io.Serializable;

/**
 * DTO for {@link TarjetaCredito}
 */
public record TarjetaDTO(String numero, EmisorTarjeta emisorTarjeta, boolean activa,
                         double fondos) implements Serializable {
    public String toString(){
        return emisorTarjeta + ": " +numero + " - Monto: $" + fondos + " - " + activa;
    }
}
