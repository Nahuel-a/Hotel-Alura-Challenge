package com.alura.modelo.usuario;

public record DatosRespuestaUsuario(Long id, String nombre, String email, String username) {
	
	public DatosRespuestaUsuario(Usuario usuario) {
		this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getUsername());
	}
}
