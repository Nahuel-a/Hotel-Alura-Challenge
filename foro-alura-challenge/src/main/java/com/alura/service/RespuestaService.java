package com.alura.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alura.infra.errores.IdNegativo;
import com.alura.infra.errores.ValidacionDeIntegridad;
import com.alura.modelo.respuesta.ActualizarDatosRespuesta;
import com.alura.modelo.respuesta.DatosRespuesta;
import com.alura.modelo.respuesta.RegistroDatosRespuesta;
import com.alura.modelo.respuesta.Respuesta;
import com.alura.modelo.respuesta.RespuestaRepository;
import com.alura.modelo.topico.TopicoRepository;
import com.alura.modelo.usuario.SesionActivaUsuario;
import com.alura.modelo.usuario.UsuarioRepository;

@Service
public class RespuestaService {
	
	@Autowired
	private RespuestaRepository respuestaRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Value("${forum.usur.username}")
	private String admin;
	
	
	public DatosRespuesta registarRespuesta(RegistroDatosRespuesta datos) {
		IdNegativo.verificar(datos.idAutor());
		IdNegativo.verificar(datos.idTopico());
		validarTopicoExistente(datos.idTopico());
		
		validarUsuarioExistente(datos.idAutor());
		validarUsuarioActivo(datos.idAutor());
				
		var topico = topicoRepository.findById(datos.idTopico()).get();
		var autor = usuarioRepository.findById(datos.idAutor()).get();
		
		var respuesta = new Respuesta(datos.mensaje(), LocalDateTime.now(), topico, autor);
		respuestaRepository.save(respuesta);
		
		return new DatosRespuesta(respuesta);
	}

	public DatosRespuesta actualizarRespuesta(ActualizarDatosRespuesta datos) {
		IdNegativo.verificar(datos.id());
		validarRespuestaExistente(datos.id());
		
		var respuesta = respuestaRepository.getReferenceById(datos.id());
		validarUsuarioActivo(respuesta.getAutor().getId());
		
		respuesta.actualizar(datos.mensaje());
		return new DatosRespuesta(respuesta);
	}
	
	public void eliminarRespuesta(Long id) {
		IdNegativo.verificar(id);
		validarRespuestaExistente(id);
		
		var respuesta = respuestaRepository.getReferenceById(id);
		validarUsuarioActivo(respuesta.getAutor().getId());
		respuesta.eliminar();
	}


	private void validarUsuarioActivo(Long id) {
		if(!id.equals(SesionActivaUsuario.idUsuario)) {
			throw new ValidacionDeIntegridad("El usuario que intenta realizar la operacion no es el que inicio la sesion");
		}
	}
	
	private void validarUsuarioExistente(Long idAutor) {
		if(!usuarioRepository.findById(idAutor).isPresent()) {
			throw new ValidacionDeIntegridad("El usuario no esta registrado en el sistema");
		}
	}

	private void validarRespuestaExistente(Long id) {
		if(!respuestaRepository.findById(id).isPresent()) {
			throw new ValidacionDeIntegridad("La respuesta no se encuentra disponible");
		}
	}

	private void validarTopicoExistente(Long idTopico) {
		if(!topicoRepository.findById(idTopico).isPresent()) {
			throw new ValidacionDeIntegridad("El topico que intenta responder no existe");
		}
	}
}
