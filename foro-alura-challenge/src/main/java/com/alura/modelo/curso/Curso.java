package com.alura.modelo.curso;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String categoria;
	private Boolean activo;
	
	
	public Curso(String nombre, String categoria) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.activo = true;
	}
	
	public void actualizar(String nombre, String categoria) {
		if(nombre != null) {
			this.nombre = nombre;
		}
		if(categoria != null) {
			this.categoria = categoria;
		}
	}
	
	public void eliminar() {
		this.activo = false;
	}
	

}
