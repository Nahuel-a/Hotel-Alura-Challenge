package com.alura.modelo.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDatosRespuesta(
		@NotBlank
		String mensaje,
		@NotNull
		Long idTopico,
		@NotNull
		Long idAutor
		) {

}
