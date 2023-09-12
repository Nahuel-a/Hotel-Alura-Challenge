package com.latam.alura.modelos;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="huespedes")
public class Huesped {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String telefono;
	private String nacionalidad;
	
	@OneToOne
	@JoinColumn(name = "numero_reserva")
	private Reserva reserva;

	public Huesped() {	}

	public Huesped(String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String nacionalidad, Reserva reserva) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.nacionalidad = nacionalidad;
		this.reserva = reserva;
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public String toString() {
		return String.format(
                "{nombre: %s, apellido: %s, fecha_nacimiento: %s, telefono: %s, nacionalidad: %s, reserva: %s  }",
                this.nombre, this.apellido, this.fechaNacimiento, this.telefono, this.nacionalidad, this.reserva
        );
	}
}