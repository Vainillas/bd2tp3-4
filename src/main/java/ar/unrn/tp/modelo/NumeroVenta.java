package ar.unrn.tp.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NumeroVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private int numero;
    private int anio;

    public NumeroVenta(int numero, int anio) {
        this.numero = numero;
        this.anio = anio;
    }
    public String getNumeroVenta(){
        return numero + "-" + anio;
    }
    public String getSiguienteNumeroVenta(){
        numero++;
        return getNumeroVenta();
    }

    public static String getNumeroVenta(int numero, int anio){
        return numero + "-" + anio;
    }
    public static String getNumeroVenta(int numero){
        return numero + "-" + LocalDateTime.now().getYear();
    }
    public static String getNumeroVenta(int numero, LocalDateTime fecha){
        return numero + "-" + fecha.getYear();
    }
}
