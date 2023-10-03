package com.alura.modelo.respuesta;

import java.time.LocalDateTime;
import com.alura.modelo.topico.Topico;
import com.alura.modelo.usuario.Usuario;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensaje;
	private LocalDateTime fechaCreacion;
	private Boolean solucion;
	private Boolean activo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_topico")
	private Topico topico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_autor")
	private Usuario autor;
	
	

	public Respuesta(String mensaje, LocalDateTime fechaCreacion, Topico topico, Usuario autor) {
		this.mensaje = mensaje;
		this.fechaCreacion = fechaCreacion;
		this.topico = topico;
		this.autor = autor;
		this.solucion = false;
		this.activo = true;
	}
	
	public void actualizar(String mensaje) {
		if(mensaje != null) {
			this.mensaje = mensaje;
		}
	}

	public void eliminar() {
		this.activo = false;
	}



}
