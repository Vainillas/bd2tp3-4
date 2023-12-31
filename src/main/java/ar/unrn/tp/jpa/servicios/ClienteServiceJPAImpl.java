package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.EmisorTarjeta;
import ar.unrn.tp.modelo.TarjetaCredito;
import ar.unrn.tp.modelo.exceptions.BusinessException;
import jakarta.persistence.EntityManagerFactory;


import java.util.ArrayList;
import java.util.List;

public class ClienteServiceJPAImpl extends ServiceJPAImpl implements ClienteService {
    private EntityManagerFactory entityManager;

    public ClienteServiceJPAImpl(EntityManagerFactory entityManager) {
        super(entityManager);
    }

    @Override
    public Cliente encontrarCliente(Long idCliente) {
        Cliente[] cliente = new Cliente[1];
        inTransactionExecute((em) -> {
            cliente[0] = em.find(Cliente.class, idCliente);
        });
        return cliente[0];
    }

    @Override
    public void crearCliente(Long id, String nombre, String apellido, String dni, String email) {
        inTransactionExecute((em) -> {
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                throw new BusinessException("El cliente ya existe");
            } else {
                Cliente c = new Cliente(id, dni, nombre, apellido, email);
                em.persist(c);
            }
        });
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String dni, String apellido, String email) {
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            if (c == null) {
                throw new BusinessException("El cliente no existe");
            } else {
                c.setDni(dni);
                c.setNombre(nombre);
                c.setApellido(apellido);
                c.setEmail(email);
                em.merge(c);
            }
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca) {
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            if (c == null) {
                throw new BusinessException("El cliente no existe");
            } else {
                //Obtener un enum mediante el nombre del enum
                EmisorTarjeta emisorTarjeta = EmisorTarjeta.valueOf(marca);
                TarjetaCredito tarjeta = new TarjetaCredito(nro, true, 0, emisorTarjeta);
                c.agregarTarjeta(tarjeta);
                em.merge(c);
            }
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca, double fondos) {
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            //Obtener un enum mediante el nombre del enum
            EmisorTarjeta emisorTarjeta = EmisorTarjeta.valueOf(marca);
            TarjetaCredito tarjeta = new TarjetaCredito(nro, true, fondos, emisorTarjeta);
            c.agregarTarjeta(tarjeta);
            em.merge(c);
        });
    }

    @Override
    public List<TarjetaCredito> listarTarjetas(Long idCliente) {
        List<TarjetaCredito> tarjetas = new ArrayList<>();
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            tarjetas.addAll(c.getTarjetas());
        });
        return tarjetas;
    }
}
