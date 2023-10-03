package com.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.modelo.curso.ActializarDatosCurso;
import com.alura.modelo.curso.RegistroDatosCurso;
import com.alura.service.CursoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
	@Autowired
	private CursoService cursoService;
	
	@PostMapping
	@Transactional
	public ResponseEntity registrarCurso(@RequestBody @Valid RegistroDatosCurso datos) {
		var curso = cursoService.registrarCurso(datos);
		return ResponseEntity.ok(curso);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity actualizarCurso(@RequestBody @Valid ActializarDatosCurso datos) {
		var curso = cursoService.actualizarCurso(datos);
		return ResponseEntity.ok(curso);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarCurso(@PathVariable @Min(1) Long id) {
		cursoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}

}
