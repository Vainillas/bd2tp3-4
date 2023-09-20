package ar.unrn.tp.main;

import ar.unrn.tp.api.*;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.EmisorTarjeta;
import ar.unrn.tp.ui.MainWindow;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");


        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        PromocionService promocionService = new PromocionServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        VentaService ventaService = new VentaServiceJPAImpl(emf, productoService, promocionService);

        clienteService.crearCliente(1L, "Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
        clienteService.agregarTarjeta(1L, "123456789", String.valueOf(EmisorTarjeta.MEMECARD), 10000);
        clienteService.agregarTarjeta(1L, "987654321", String.valueOf(EmisorTarjeta.PATAGONIA), 1500000);

        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(1L, "XQ-123", "Juguete de Quetzalcoatlus", 1000.0, Categoria.JUGUETES, marcaService.encontrarMarca("DC Comics"));
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));

        promocionService.crearDescuento(1L, "DC Comics", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.1);
        promocionService.crearDescuentoSobreTotal(1L, String.valueOf(EmisorTarjeta.PATAGONIA), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.2);


        MainWindow mainWindow = new MainWindow(ventaService, productoService, marcaService, promocionService, clienteService, 1L);
        mainWindow.loadUp();
    }
}
