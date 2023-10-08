package ar.unrn.tp.main;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.jpa.servicios.MarcaServiceJPAImpl;
import ar.unrn.tp.jpa.servicios.ProductoServiceJPAImpl;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.ui.EditProductWindow;
import ar.unrn.tp.ui.MainWindow;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainTP5 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        ProductoService productoService = new ProductoServiceJPAImpl(emf);
        MarcaService marcaService = new MarcaServiceJPAImpl(emf);
        marcaService.crearMarca("DC Comics");
        productoService.crearProducto(1L, "XQ-123", "Juguete 1", 1000.0, Categoria.JUGUETES, marcaService.encontrarMarca("DC Comics"));
        productoService.crearProducto(2L, "XQ-124", "Juguete 2", 1000.0, Categoria.JUGUETES,marcaService.encontrarMarca("DC Comics"));
        EditProductWindow mainWindow = new EditProductWindow( productoService, marcaService);
        try{
            mainWindow.loadUp();
        }catch(Exception e){
            System.out.println("Error al cargar la ventana principal");
            e.printStackTrace();
        }

    }
}
