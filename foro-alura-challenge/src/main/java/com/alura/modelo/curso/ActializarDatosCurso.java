package com.alura.modelo.curso;


import jakarta.validation.constraints.NotNull;

public record ActializarDatosCurso(
		@NotNull
		Long id,
		String nombre,
		String categoria
		) {

}
