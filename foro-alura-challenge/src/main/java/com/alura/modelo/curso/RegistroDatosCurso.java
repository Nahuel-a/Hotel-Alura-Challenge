package com.alura.modelo.curso;

import jakarta.validation.constraints.NotBlank;

public record RegistroDatosCurso(
		@NotBlank
		String nombre,
		@NotBlank
		String categoria) {

}
