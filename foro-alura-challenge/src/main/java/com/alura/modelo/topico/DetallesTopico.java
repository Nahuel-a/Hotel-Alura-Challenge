package com.alura.modelo.topico;

import java.time.LocalDateTime;
import java.util.List;

import com.alura.modelo.respuesta.ListarDatosRespuesta;
import com.alura.modelo.status.StatusTopico;

public record DetallesTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, 
		StatusTopico status, String autor, String curso, List<ListarDatosRespuesta> respuesta) {

}
