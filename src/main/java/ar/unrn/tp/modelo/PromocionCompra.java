package ar.unrn.tp.modelo;

import ar.unrn.tp.modelo.exceptions.BusinessException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
public class PromocionCompra implements Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private double DESCUENTO = 0.08;
    private LocalDate diaInicio;
    private LocalDate diaFin;
    @Enumerated(EnumType.STRING)
    private EmisorTarjeta emisorTarjeta;


    public PromocionCompra(LocalDate diaInicio, LocalDate diaFin, EmisorTarjeta emisorTarjeta) {
        validarAtributosPromocion(diaInicio, diaFin);
        this.diaInicio = diaInicio;
        this.diaFin = diaFin;
        this.emisorTarjeta = emisorTarjeta;
    }
    public PromocionCompra(LocalDate diaInicio, LocalDate diaFin, EmisorTarjeta emisorTarjeta, double descuento) {
        this(diaInicio, diaFin, emisorTarjeta);
        this.DESCUENTO = descuento;
    }
    public PromocionCompra(Long id, LocalDate diaInicio, LocalDate diaFin, EmisorTarjeta emisorTarjeta, double descuento) {
        this(diaInicio, diaFin, emisorTarjeta,descuento);
        this.id = id;
    }

    @Override
    public double aplicarPromocion(List<Producto> productos, TarjetaCredito tarjetaCredito) {
        double descuento = 0;
            if((diaInicio.isEqual(LocalDate.now()) && diaFin.isAfter(LocalDate.now())) || (diaInicio.isBefore(LocalDate.now()) && diaFin.isEqual(LocalDate.now())) || (diaInicio.isBefore(LocalDate.now()) && diaFin.isAfter(LocalDate.now()))) {
                for(Producto producto : productos)
                    if (tarjetaCredito.esEmisor(emisorTarjeta)) {
                        descuento -= producto.getPrecio() - producto.aplicarDescuento(DESCUENTO);
                    }
            }
            return descuento;
    }

    @Override
    public double aplicarPromocion(List<Producto> productos) {
        return 0;
    }

    @Override
    public double getDescuento() {
        return DESCUENTO;
    }

    @Override
    public boolean estaActiva(LocalDate dia) {
        return (diaInicio.isEqual(dia) && diaFin.isAfter(dia)) || (diaInicio.isBefore(dia) && diaFin.isEqual(dia)) || (diaInicio.isBefore(dia) && diaFin.isAfter(dia));
    }

    @Override
    public boolean estaActivaHoy() {
        return estaActiva(LocalDate.now());
    }

    public void validarAtributosPromocion(LocalDate diaInicio, LocalDate diaFin) {
        if (diaInicio == null) {
            throw new BusinessException("El dia de inicio no puede ser nulo");
        }
        if (diaFin == null) {
            throw new BusinessException("El dia de fin no puede ser nulo");
        }
        if (diaInicio.isAfter(diaFin)) {
            throw new BusinessException("El dia de inicio no puede ser posterior al dia de fin");
        }
    }
    @Override
    public String toString(){
        return "Promocion de compra con tarjeta de credito " + emisorTarjeta.toString() + " con descuento de " + DESCUENTO;
    }

}
