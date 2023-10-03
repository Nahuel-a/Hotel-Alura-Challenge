package com.alura.modelo.respuesta;

import jakarta.validation.constraints.NotNull;

public record ActualizarDatosRespuesta(
		@NotNull
		Long id,
		String mensaje
		) {

}
