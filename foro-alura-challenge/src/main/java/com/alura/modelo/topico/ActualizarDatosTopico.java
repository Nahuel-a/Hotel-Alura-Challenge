package com.alura.modelo.topico;

import com.alura.modelo.status.StatusTopico;

import jakarta.validation.constraints.NotNull;

public record ActualizarDatosTopico(
		@NotNull
		Long id,
		String titulo,
		String mensaje,
		StatusTopico status,
		Long idCurso
		) {
}
