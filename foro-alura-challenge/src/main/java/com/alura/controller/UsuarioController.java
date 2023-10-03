package com.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.modelo.usuario.RegistrarDatosUsuario;
import com.alura.service.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/usuarios")
//Ver esta parte
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
	
	@Autowired
	UsuarioService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity registrarUsuario(@RequestBody @Valid RegistrarDatosUsuario datos) {
		var usuario = service.registrarUsuario(datos);
		
		return ResponseEntity.ok(usuario);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarUsuario(@PathVariable @Min(1) Long id) {
		service.eliminarUsuario(id);
		
		return ResponseEntity.noContent().build();
	}
}
