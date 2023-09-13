package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProductoServiceTest {
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
    public void persistirProducto() {
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(1L, "XQ-123", "Juguete de Quetzalcoatlus", 1000.0, Categoria.JUGUETES);
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));
        Producto dinosaurio = productoService.encontrarProducto(1L);
        Producto batman = productoService.encontrarProducto(2L);
        Assertions.assertEquals("XQ-123", dinosaurio.getCodigo());
        Assertions.assertEquals("XQ-124", batman.getCodigo());
        Assertions.assertTrue(batman.getMarca().esIgualNombre("DC Comics"));
        Assertions.assertThrows(NullPointerException.class, () -> {
            dinosaurio.getMarca().esIgualNombre("Mattel");
        });
    }
    @Test
    public void modificarProducto(){
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));
        productoService.modificarProducto(2L, "XQ-123", "Juguete de Quetzalcoatlus", Categoria.JUGUETES,"DC Comics",1000.0);
        Producto dinosaurio = productoService.encontrarProducto(2L);
        Assertions.assertEquals("XQ-123", dinosaurio.getCodigo());
        Assertions.assertTrue(dinosaurio.getMarca().esIgualNombre("DC Comics"));
    }
    @Test
    public void persistirProductoRepetido(){
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));
        Assertions.assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(2L, "XQ-123", "Juguete de Quetzalcoatlus", 1000.0, Categoria.JUGUETES);
        });
    }
    @Test
    public void listarProductos(){
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        marcaService.crearMarca("DC Comics");
        marcaService.crearMarca("Marvel");
        productoService.crearProducto(2L, "XQ-124", "Juguete de Batman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));
        productoService.crearProducto(3L, "XQ-125", "Juguete de Superman", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));
        productoService.crearProducto(4L, "XQ-126", "Juguete de Punisher", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("Marvel"));
        Assertions.assertEquals(3, productoService.listarProductos().size());
    }



}
