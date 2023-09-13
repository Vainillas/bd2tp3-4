package ar.unrn.tp.modelo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

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
