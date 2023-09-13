package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class ProductoServiceJPAImpl extends ServiceJPAImpl implements ProductoService  {
    private EntityManagerFactory entityManager;

    public ProductoServiceJPAImpl(EntityManagerFactory entityManager){
        super(entityManager);
    }

    @Override
    public Producto encontrarProducto(Long idProducto) {
        Producto[] producto = new Producto[1];
        inTransactionExecute((em) -> {
            producto[0] = em.find(Producto.class, idProducto);
        });
        return producto[0];
    }

    @Override
    public void crearProducto(Long ID, String codigo, String descripcion, double precio, Categoria categoria) {
        inTransactionExecute((em) -> {
            Producto p = em.find(Producto.class, ID);
            if(p!= null){
                throw new RuntimeException("El producto ya existe");
            }else {
                Producto producto = new Producto(ID, codigo, descripcion, categoria, precio);
                em.persist(producto);
            }
        });
    }

    @Override //Validar que sea una categoría existente y que codigo no se repita
    public void crearProducto(Long ID,String codigo, String descripcion, double precio, Categoria categoria, Marca marca) {
        inTransactionExecute((em) -> {
            Producto producto = new Producto(ID, codigo,descripcion,categoria, marca, precio);
            em.persist(producto);
        });

    }

    @Override
    public void modificarProducto(Long idProducto, String codigo, String descripcion, Categoria categoria, String idMarca, double precio) {
            inTransactionExecute((em) -> {
                Marca m = em.find(Marca.class, idMarca);
                Producto producto = em.find(Producto.class, idProducto);
                producto.setCodigo(codigo);
                producto.setDescripcion(descripcion);
                producto.setCategoria(categoria);
                producto.setPrecio(precio);
                producto.setMarca(m);
                em.persist(producto);
            });
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        inTransactionExecute((em) -> {
            productos.addAll(em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList());
        });
        return productos;
    }
    @Override
    public List<Producto> encontrarProductos(List<Long> idProductos){
        List<Producto> productos = new ArrayList<>();
        inTransactionExecute((em) -> {
            for (Long idProducto : idProductos) {
                Producto producto = em.find(Producto.class, idProducto);
                productos.add(producto);
            }
        });
        return productos;
    }
}
