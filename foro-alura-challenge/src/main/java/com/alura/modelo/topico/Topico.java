package com.alura.modelo.topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.alura.modelo.curso.Curso;
import com.alura.modelo.respuesta.Respuesta;
import com.alura.modelo.status.StatusTopico;
import com.alura.modelo.usuario.Usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;
	private Boolean activo;
	private LocalDateTime fechaCreacion;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusTopico status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_autor")
	private Usuario autor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
	private List<Respuesta> respuestas = new ArrayList<>();

	public Topico(String titulo, String mensaje, LocalDateTime fechaCreacion, Usuario autor, Curso curso) {
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.fechaCreacion = fechaCreacion;
		this.status = StatusTopico.NO_RESPONDIDO;
		this.autor = autor;
		this.curso = curso;
		this.activo = true;
	}
	
	public void actualizar(String titulo, String mensaje,StatusTopico status, Curso curso) {
		if(titulo != null) {
			this.titulo = titulo;
		}
		if(mensaje != null) {
			this.mensaje = mensaje;
		}
		if(status != null) {
			this.status = status;
		}
		if(curso != null) {
			this.curso = curso;
		}
	}
	
	public void eliminar() {
		this.activo = false;
	}
	

}
