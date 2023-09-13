package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.TarjetaCredito;

import java.util.List;

public interface ClienteService {
    Cliente encontrarCliente(Long idCliente);
    // validar que el dni no se repita
    void crearCliente(Long id, String nombre, String apellido, String dni, String email);
    // validar que sea un cliente existente
    void modificarCliente(Long idCliente, String nombre,String dni, String apellido, String email);
    // validar que sea un cliente existente
    void agregarTarjeta(Long idCliente, String nro, String marca);

    //Devuelve las tarjetas de un cliente específico
    List<TarjetaCredito> listarTarjetas(Long idCliente);
}
