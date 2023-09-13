package ar.unrn.tp.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Cliente {
    private String dni;

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String nombre;
    private String apellido;
    private String email;
    //Sacar el fetchtype eager
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TarjetaCredito> tarjetas = new ArrayList<>();



    public Cliente(String dni, String nombre, String apellido, String email, List<TarjetaCredito> tarjetas) {
        this(dni, nombre, apellido, email);
        this.tarjetas = tarjetas;
    }
    public Cliente(Long id, String dni, String nombre, String apellido, String email) {
        validarAtributosCliente(dni, nombre, apellido, email);
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
    }
    public Cliente(String dni, String nombre, String apellido, String email) {
        validarAtributosCliente(dni, nombre, apellido, email);
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
    }
    //Constructor con una sola tarjeta de credito
    public Cliente(String dni, String nombre, String apellido, String email, TarjetaCredito tarjeta) {
        this(dni, nombre, apellido, email);
        agregarTarjeta(tarjeta);
    }
    public Cliente(Long id, String dni, String nombre, String apellido, String email, List<TarjetaCredito> tarjetas) {
        this(id, dni, nombre, apellido, email);
        this.tarjetas = tarjetas;
    }
    public void validarAtributosCliente(String dni, String nombre, String apellido, String email) {
        if (dni == null || dni.isEmpty()) {
            throw new RuntimeException("El dni no puede ser nulo o vacio");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre no puede ser nulo o vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new RuntimeException("El apellido no puede ser nulo o vacio");
        }//Validar que el email tenga un formato válido con regex
        if (email == null || email.isEmpty() || !email.matches("^(.+)@(.+)$")) {
            throw new RuntimeException("El email no puede ser nulo o vacio");
        }
    }
    public void agregarTarjeta(TarjetaCredito tarjeta) {
        tarjetas.add(tarjeta);
    }



    public boolean seLlama(String nombre) {
        return this.nombre.equals(nombre);
    }
    public boolean seApellida(String apellido) {
        return this.apellido.equals(apellido);
    }
    public boolean tieneEmail(String email) {
        return this.email.equals(email);
    }




}
