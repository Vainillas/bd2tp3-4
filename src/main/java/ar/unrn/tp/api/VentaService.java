package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Venta;

import java.util.List;

public interface VentaService {
        Venta encontrarVenta(Long idVenta);
        //Crea una venta. El monto se calcula aplicando los descuentos a la fecha
        // validaciones:
        // - debe ser un cliente existente
        // - la lista de productos no debe estar vacía
        // - La tarjeta debe pertenecer al cliente
        void realizarVenta(Long idVenta, Long idCliente, List<Long> productos, Long idTarjeta);
        //Crea una venta. El monto se calcula aplicando los descuentos a la fecha
        // validaciones:
        // - debe ser un cliente existente
        // - la lista de productos no debe estar vacía
        // - La tarjeta debe pertenecer al cliente
        void realizarVenta(Long idCliente, List<Long> productos, String numeroTarjeta);
        //Devuelve el monto total aplicando los descuentos al día de la fecha
        // validar que no llegue una lista vacía y la tarjeta exista
        double calcularMonto(List<Long> productos, Long idTarjeta);

        double calcularMonto(List<Long> productos, String numeroTarjeta);

        //Devuelve todas las ventas realizadas
        List<Venta> ventas();

        //Devuelve las últimas 3 ventas de un cliente
        List<Venta> ultimas3Ventas(Long idCliente);
}
