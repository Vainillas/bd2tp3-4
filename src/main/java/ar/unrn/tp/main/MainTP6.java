package ar.unrn.tp.main;

import ar.unrn.tp.api.*;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.EmisorTarjeta;
import ar.unrn.tp.ui.MainWindow;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.util.List;


public class MainTP6 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        Jedis jedis = new Jedis();


        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        PromocionService promocionService = new PromocionServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        VentaRedisService ventaRedisService = new VentaRedisServiceJPAImpl(emf, jedis);
        VentaService ventaService = new VentaServiceJPAImpl(emf, productoService, promocionService, ventaRedisService);

        clienteService.crearCliente(1L, "Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
        clienteService.agregarTarjeta(1L, "123456789", String.valueOf(EmisorTarjeta.MEMECARD), 10000);
        clienteService.agregarTarjeta(1L, "987654321", String.valueOf(EmisorTarjeta.PATAGONIA), 1500000);

        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(1L, "XQ-123", "Juguete de Quetzalcoatlus", 1000.0, Categoria.JUGUETES, marcaService.encontrarMarca("DC Comics"));
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));

        promocionService.crearDescuento(1L, "DC Comics", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.1);
        promocionService.crearDescuentoSobreTotal(1L, String.valueOf(EmisorTarjeta.PATAGONIA), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.2);



        MainWindow mainWindow = new MainWindow(ventaService, productoService, marcaService, promocionService, clienteService, 1L);
        try{
            mainWindow.loadUp();
        }catch(Exception e){
            System.out.println("Error al cargar la ventana principal");
            e.printStackTrace();
        }
    }

    @Test
    public void testJedisVentas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        Jedis jedis = new Jedis();


        ClienteService clienteService = new ClienteServiceJPAImpl(emf);
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        PromocionService promocionService = new PromocionServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        VentaRedisService ventaRedisService = new VentaRedisServiceJPAImpl(emf, jedis);
        VentaService ventaService = new VentaServiceJPAImpl(emf, productoService, promocionService, ventaRedisService);

        clienteService.crearCliente(1L, "Mateo", "Aliberti", "43303613", "maliberti@unrn.edu.ar");
        clienteService.agregarTarjeta(1L, "123456789", String.valueOf(EmisorTarjeta.MEMECARD), 10000);
        clienteService.agregarTarjeta(1L, "987654321", String.valueOf(EmisorTarjeta.PATAGONIA), 1500000);

        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(1L, "XQ-123", "Juguete de Quetzalcoatlus", 1000.0, Categoria.JUGUETES, marcaService.encontrarMarca("DC Comics"));
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES, marcaService.encontrarMarca("DC Comics"));

        promocionService.crearDescuento(1L, "DC Comics", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.1);
        promocionService.crearDescuentoSobreTotal(1L, String.valueOf(EmisorTarjeta.PATAGONIA), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 0.2);


        ventaService.realizarVenta(1L, List.of(1L, 1L, 2L), "123456789");
        Assert.assertEquals(ventaService.ultimas3Ventas(1L).size(), 1);
        ventaService.realizarVenta(1L, List.of(1L, 1L, 2L), "123456789");
        Assert.assertEquals(ventaService.ultimas3Ventas(1L).size(), 2);
        ventaService.realizarVenta(1L, List.of(1L, 1L, 2L), "123456789");
        Assert.assertEquals(ventaService.ultimas3Ventas(1L).size(), 3);


    }

}
