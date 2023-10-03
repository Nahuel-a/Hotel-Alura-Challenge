package com.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.modelo.respuesta.RespuestaRepository;
import com.alura.modelo.topico.ActualizarDatosTopico;
import com.alura.modelo.topico.ListarDatosTopico;
import com.alura.modelo.topico.RegistroDatosTopico;
import com.alura.modelo.topico.TopicoRepository;
import com.alura.service.TopicoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
	@Autowired
	private TopicoService topicoService;
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private RespuestaRepository respuestaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity registrarTopico(@RequestBody @Valid RegistroDatosTopico datos) {
		var topico = topicoService.registrarTopico(datos);
		
		return ResponseEntity.ok(topico);
	}
	
	@GetMapping
	@Transactional
	public ResponseEntity<Page<ListarDatosTopico>> listarTopicos(@PageableDefault(size = 20, sort= {"id"}) Pageable pageable){
		var topicos = topicoService.listarTopicos(pageable);
		return ResponseEntity.ok(topicos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> retornoRespuestasTopico(@PathVariable @Min(1) Long id){
		var topicoRespuestas = topicoService.detallesTopico(id);
		return ResponseEntity.ok(topicoRespuestas);	
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity actualizarTopico(@RequestBody @Valid ActualizarDatosTopico datos) {
		var topico = topicoService.actualizarTopico(datos);
		
		return ResponseEntity.ok(topico);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarTopico(@PathVariable @Min(1) Long id) {
		topicoService.eliminarTopico(id);
		return ResponseEntity.noContent().build();
	}
}
