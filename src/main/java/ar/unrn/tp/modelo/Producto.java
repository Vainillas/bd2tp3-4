package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.exceptions.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;



@Getter
@Setter
@Entity
@NoArgsConstructor
public class Producto {
    @Id
    private Long id;
    private String codigo;
    private String descripcion;
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "nombre")
    private Marca marca;
    private double precio;

    public Producto(String codigo, String descripcion, Categoria categoria, double precio){

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }
    public Producto(Long id, String codigo, String descripcion, Categoria categoria, double precio){
        this(codigo, descripcion, categoria, precio);
        this.id = id;
    }
    public Producto(String codigo, String descripcion, Categoria categoria, Marca marca, double precio) {
        this(codigo, descripcion, categoria, precio);
        validarAtributosConstructor(descripcion, categoria, precio, marca);
        this.marca = marca;
    }
    public Producto(Long id, String codigo, String descripcion, Categoria categoria, Marca marca, double precio) {
        this(codigo, descripcion, categoria, marca, precio);
        this.id = id;
    }

    private void validarAtributosConstructor(String descripcion, Categoria categoria, double precio, Marca marca) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new BusinessException("La descripcion no puede ser nula o vacia");
        }
        if (categoria == null) {
            throw new BusinessException("La categoria no puede ser nula");
        }
        if (precio <= 0) {
            throw new BusinessException("El precio no puede ser menor o igual a cero");
        }
        if (marca == null) {
            throw new BusinessException("La marca no puede ser nula");
        }
    }



    public boolean sosDeMarca(Marca marca) {
        return this.marca.equals(marca);
    }
    public double aplicarDescuento(double descuento){
        return precio - (precio * descuento);
    }
}
