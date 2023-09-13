package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;


public interface Promocion {
    double aplicarPromocion(List<Producto> productos, TarjetaCredito tarjetaCredito);
    double aplicarPromocion(List<Producto> productos);
    double getDescuento();
    boolean estaActiva(LocalDate dia);
    boolean estaActivaHoy();

}
