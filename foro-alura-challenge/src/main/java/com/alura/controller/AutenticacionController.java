package com.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.infra.security.DatosJWTToken;
import com.alura.infra.security.TokenService;
import com.alura.modelo.usuario.RegistrarDatosUsuario;
import com.alura.modelo.usuario.Usuario;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
@Tag(name = "autenticacion", description = "Obtiene el token para el usuario")
public class AutenticacionController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity autenticarUsuario(@RequestBody @Valid RegistrarDatosUsuario datos) {
		Authentication autoToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
		
		var usuarioAutenticado = authenticationManager.authenticate(autoToken);
		var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
		
		return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
	}
}
