package ar.unrn.tp.main;

import ar.unrn.tp.api.*;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.ui.MainWindow;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");

        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        PromocionService promocionService = new PromocionServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        VentaService ventaService = new VentaServiceJPAImpl(emf, productoService, promocionService);

        MainWindow mainWindow = new MainWindow(ventaService, productoService, marcaService, promocionService, clienteService, 1L);
        mainWindow.loadUp();
    }
}
