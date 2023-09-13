package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.EmisorTarjeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClienteServiceTest {
    private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
    }
    @AfterEach
    public void tearDown() {
        emf.close();
    }
    @Test
    public void persistirCliente() {
        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        clienteService.crearCliente(1L,"Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
        Cliente c = clienteService.encontrarCliente(1L);
        Assertions.assertEquals("Mateo", c.getNombre());
    }
    @Test
    public void modificarCliente() {
        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        clienteService.crearCliente(1L, "Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
        clienteService.modificarCliente(1L, "Mateo", "Aliberti", "43303613", "mateoaliberti1@gmail.com");
        Cliente c = clienteService.encontrarCliente(1L);
        Assertions.assertEquals("mateoaliberti1@gmail.com", c.getEmail());
    }
    @Test
    public void agregarTarjetaACliente(){
        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        clienteService.crearCliente(1L, "Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
        clienteService.agregarTarjeta(1L, "123456789", String.valueOf(EmisorTarjeta.MEMECARD));
        Cliente c = clienteService.encontrarCliente(1L);
        Assertions.assertEquals(1, c.getTarjetas().size());
    }
}
