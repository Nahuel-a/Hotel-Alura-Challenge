package com.alura.modelo.respuesta;

import java.time.LocalDateTime;

public record ListarDatosRespuesta(Long id, String mensaje, 
		LocalDateTime fechaCreacion, String autor, Boolean solucion) {
	
	public ListarDatosRespuesta(Respuesta respuesta) {
		this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(), 
				respuesta.getAutor().getNombre(), respuesta.getSolucion());
		
	}

}
