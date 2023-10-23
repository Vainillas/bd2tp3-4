package ar.unrn.tp.main;

import ar.unrn.tp.modelo.Venta;

import java.util.List;

public interface VentaRedisService{
    //Devuelve las últimas 3 ventas de un cliente
    List<Venta> ultimas3Ventas(Long idCliente);

    void actualizarVentas(Long idCliente);
}
