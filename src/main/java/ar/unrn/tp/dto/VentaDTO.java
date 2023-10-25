package ar.unrn.tp.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ar.unrn.tp.modelo.Venta}
 */
public record VentaDTO(Long id, String numero, LocalDateTime fechaHora, String nombreCliente,
                       int cantidadProductos, double montoTotal) implements Serializable {

    @Override
    public String toString() {
        return "Venta " + numero + " - Fecha y Hora: " + fechaHora + " - Cliente: " + nombreCliente + " - Cantidad Productos: " + cantidadProductos + " - Monto: $" + montoTotal;
    }
}
