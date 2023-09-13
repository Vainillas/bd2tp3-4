package ar.unrn.tp.api;


import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;

import java.util.List;

public interface ProductoService {
        Producto encontrarProducto(Long idProducto);

        //validar que sea una categoría existente y que codigo no se repita
        void crearProducto(Long ID, String codigo, String descripcion, double precio, Categoria categoria);

        //Validar que sea una categoría existente y que codigo no se repita
        void crearProducto(Long ID, String codigo, String descripcion, double precio, Categoria categoria, Marca marca);

        //validar que sea un producto existente
        void modificarProducto(Long idProducto, String codigo,  String descripcion, Categoria categoria, String idMarca, double precio);
        //Devuelve todos los productos
        List<Producto> listarProductos();

        List<Producto> encontrarProductos(List<Long> idProductos);
}
