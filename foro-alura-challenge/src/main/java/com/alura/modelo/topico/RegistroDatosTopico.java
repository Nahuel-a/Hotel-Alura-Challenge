package com.alura.modelo.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDatosTopico(
		@NotBlank
		String titulo,
		@NotBlank
		String mensaje,
		@NotNull
		Long idAutor,
		@NotNull
		Long idCurso
		) {
}
