package ar.unrn.tp.modelo;
;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime fechaHora;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    //Sacar el fetchtype eager
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductoHistorico> listaProductos;
    private double montoTotal;

    public Venta(LocalDateTime fechaHora, Cliente cliente, List<Producto> listaProductosHistoricos, double montoTotal) {
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.listaProductos = convertirProductosAHistorico(listaProductosHistoricos);
        this.montoTotal = montoTotal;
    }
    public Venta(Long id, LocalDateTime fechaHora, Cliente cliente, List<Producto> listaProductosHistoricos, double montoTotal) {
        this(fechaHora, cliente, listaProductosHistoricos, montoTotal);
        this.id = id;
    }
    public List<ProductoHistorico> convertirProductosAHistorico(List<Producto> listaProductos){
        return List.of(listaProductos.stream().map(producto -> new ProductoHistorico(producto.getId(), producto.getCodigo(), producto.getDescripcion(), producto.getCategoria(), producto.getMarca(), producto.getPrecio())).toArray(ProductoHistorico[]::new));
    }

}
