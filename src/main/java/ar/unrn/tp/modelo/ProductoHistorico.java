package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class ProductoHistorico{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String codigo;
    private String descripcion;
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "nombre")
    private Marca marca;
    private double precio;

    public ProductoHistorico(String codigo, String descripcion, Categoria categoria, Marca marca, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.marca = marca;
    }
}
