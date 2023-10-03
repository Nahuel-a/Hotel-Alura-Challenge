package com.alura.modelo.topico;

import java.time.LocalDateTime;


import com.alura.modelo.status.StatusTopico;

public record ListarDatosTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, 
		StatusTopico status, String autor, String curso) {

	
	public ListarDatosTopico(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), 
				topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre());
	}
	
}
