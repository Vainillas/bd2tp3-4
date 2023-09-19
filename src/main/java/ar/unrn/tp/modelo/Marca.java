package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Marca {
    @Id
    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public Marca() {

    }
    public boolean esIgualNombre(String nombre){
        return this.nombre.equals(nombre);
    }
}
