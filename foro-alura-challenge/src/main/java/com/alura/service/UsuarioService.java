package com.alura.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alura.infra.errores.IdNegativo;
import com.alura.infra.errores.ValidacionDeIntegridad;
import com.alura.modelo.usuario.SesionActivaUsuario;
import com.alura.modelo.usuario.RegistrarDatosUsuario;
import com.alura.modelo.usuario.DatosRespuestaUsuario;
import com.alura.modelo.usuario.Usuario;
import com.alura.modelo.usuario.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public DatosRespuestaUsuario registrarUsuario(RegistrarDatosUsuario datos) {
		validarEmail(datos.email());
		validarUsername(datos.username());
		
		var usuario = new Usuario(datos.nombre(), datos.email(), datos.username(), datos.password());
		String passwordSecuredHash = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(passwordSecuredHash);
		usuarioRepository.save(usuario);
		
		return new DatosRespuestaUsuario(usuario);
	}

	public void eliminarUsuario(Long id) {
		IdNegativo.verificar(id);
		validarUsuario(id);
	}

	private void validarUsuario(Long id) {
		if(!id.equals(SesionActivaUsuario.idUsuario)) {
			throw new ValidacionDeIntegridad("El usuario que intenta modificar no es el mismo que inicio sesion");
		}
	}
	
	private void validarUsername(String username) {
		if(username != null && usuarioRepository.existsByUsername(username)) {
			throw new ValidacionDeIntegridad("El nombre de usuario ya fue registrado");
		}
	}
	
	private void validarEmail(String email) {
		if(email != null && usuarioRepository.existsByEmail(email)) {
			throw new ValidacionDeIntegridad("El email ya fue registrado");
		}
		
	}
}
