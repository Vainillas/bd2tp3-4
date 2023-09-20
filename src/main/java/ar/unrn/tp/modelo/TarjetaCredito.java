package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@Entity
public class TarjetaCredito {

    private Long id;
    @Id
    @Column(name = "numero", nullable = false)
    private String numero;
    @Enumerated(EnumType.STRING)
    private EmisorTarjeta emisorTarjeta;
    private boolean activa;
    private double fondos;

    public TarjetaCredito(Long id, String numero, boolean activa, double fondos, EmisorTarjeta emisorTarjeta) {
        this(numero, activa, fondos, emisorTarjeta);
        this.id = id;
    }
    public TarjetaCredito(String numero, boolean activa, double fondos, EmisorTarjeta emisorTarjeta) {
        this(activa, fondos, emisorTarjeta);
        this.numero = numero;
    }

    public TarjetaCredito(boolean activa, double fondos, EmisorTarjeta emisorTarjeta) {
        this.activa = activa;
        this.fondos = fondos;
        this.emisorTarjeta = emisorTarjeta;
    }


    public boolean estaActiva() {
        return activa;
    }

    public boolean tieneFondosSuficientes(double monto) {
        return fondos >= monto;
    }
    public void descontarFondos(double monto) {
        fondos -= monto;
    }
    public void sumarFondos(double monto) {
        fondos += monto;
    }

    public void pagar(double v) {
        this.descontarFondos(v);
    }

    public boolean esEmisor(EmisorTarjeta emisorTarjeta) {
        return this.emisorTarjeta.equals(emisorTarjeta);
    }
}
