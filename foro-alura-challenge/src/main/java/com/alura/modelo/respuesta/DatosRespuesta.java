package com.alura.modelo.respuesta;

import java.time.LocalDateTime;

public record DatosRespuesta(Long id, String mensaje, LocalDateTime fechaCreacion, 
		String topico, String autor, Boolean solucion) {
	
	public DatosRespuesta(Respuesta respuesta) {
		this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(), 
				respuesta.getTopico().getTitulo(), respuesta.getAutor().getNombre(), 
				respuesta.getSolucion());
	}
}
