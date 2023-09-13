package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

public class PromocionCollector {
    private List<Promocion> promociones = new ArrayList<>();

    public PromocionCollector() {
    }
    public PromocionCollector(List<Promocion> promociones) {
        agregarPromociones(promociones);
    }

    public double retornarTotal(List<Producto> productos, TarjetaCredito tarjetaCredito) {
        double descuento = 0;
        double total = 0;
        for(Producto producto : productos) {
            total += producto.getPrecio();
        }
        for (Promocion promocion : promociones) {
            descuento += promocion.aplicarPromocion(productos, tarjetaCredito);
        }
        return total+descuento;
    }
    public double retornarTotal(List<Producto> productos) {
        double descuento = 0;
        double total = 0;
        for(Producto producto : productos) {
            total += producto.getPrecio();
        }
        for (Promocion promocion : promociones) {
            descuento += promocion.aplicarPromocion(productos);
        }
        return total+descuento;
    }
    //Método para añadir promociones que valide que no se pueden agregar más de una promoción de tipo PromocionCompra
    public void agregarPromocion(Promocion promocion) {
        if(promocion instanceof PromocionCompra) {
            if(promociones.stream().noneMatch(promocion1 -> promocion1 instanceof PromocionCompra)) {
                promociones.add(promocion);
            }
        } else {
            promociones.add(promocion);
        }
    }
    //Método que acepte una Lista de promociones y valide que no hay más de una promoción de tipo PromocionCompra
    public void agregarPromociones(List<Promocion> promociones) {
        for (Promocion promocion : promociones) {
            agregarPromocion(promocion);
        }
    }


    //Método para recuperar las promociones que sean de tipo PromocionProducto
    private List<Promocion> retornarPromocionesProducto() {
        List<Promocion> promocionesProducto = new ArrayList<>();
        for (Promocion promocion : promociones) {
            if(promocion instanceof PromocionProducto) {
                promocionesProducto.add(promocion);
            }
        }
        return promocionesProducto;
    }
    //Método para recuperar las promociones que sean de tipo PromocionCompra
    private List<Promocion> retornarPromocionesCompra() {
        List<Promocion> promocionesCompra = new ArrayList<>();
        for (Promocion promocion : promociones) {
            if(promocion instanceof PromocionCompra) {
                promocionesCompra.add(promocion);
            }
        }
        return promocionesCompra;
    }
    //Método para calcular el descuento total de las promociones de tipo PromocionCompra
    private double retornarTotalPromocionesCompra(List<Producto> productos, double descuento) {
        for (Promocion promocion : retornarPromocionesCompra()) {
            descuento += promocion.aplicarPromocion(productos);
        }
        return descuento;
    }
}
