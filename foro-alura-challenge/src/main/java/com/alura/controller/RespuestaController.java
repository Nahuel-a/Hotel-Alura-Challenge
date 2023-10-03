package com.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.modelo.respuesta.ActualizarDatosRespuesta;
import com.alura.modelo.respuesta.RegistroDatosRespuesta;
import com.alura.service.RespuestaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
	@Autowired
	private RespuestaService respuestaService;
	
	@PostMapping
	@Transactional
	public ResponseEntity registrarRespuesta(@RequestBody @Valid RegistroDatosRespuesta datos) {
		var respuesta = respuestaService.registarRespuesta(datos);
		
		return ResponseEntity.ok(respuesta);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity actualizarRespuesta(@RequestBody @Valid ActualizarDatosRespuesta datos) {
		var respuesta = respuestaService.actualizarRespuesta(datos);
		
		return ResponseEntity.ok(respuesta);
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity eliminarRespuesta(@PathVariable @Min(1) Long id) {
		respuestaService.eliminarRespuesta(id);
		return ResponseEntity.noContent().build();
	}
}
