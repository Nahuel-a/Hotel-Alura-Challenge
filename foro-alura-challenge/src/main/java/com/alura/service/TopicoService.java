package com.alura.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alura.infra.errores.IdNegativo;
import com.alura.infra.errores.ValidacionDeIntegridad;
import com.alura.modelo.curso.CursoRepository;
import com.alura.modelo.respuesta.ListarDatosRespuesta;
import com.alura.modelo.respuesta.Respuesta;
import com.alura.modelo.respuesta.RespuestaRepository;
import com.alura.modelo.topico.ActualizarDatosTopico;
import com.alura.modelo.topico.DatosRespuestaTopico;
import com.alura.modelo.topico.DetallesTopico;
import com.alura.modelo.topico.ListarDatosTopico;
import com.alura.modelo.topico.RegistroDatosTopico;
import com.alura.modelo.topico.Topico;
import com.alura.modelo.topico.TopicoRepository;
import com.alura.modelo.usuario.SesionActivaUsuario;
import com.alura.modelo.usuario.UsuarioRepository;

@Service
public class TopicoService {
	
	@Autowired
	private RespuestaRepository respuestaRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Value("${forum.usur.username}")
	private String admin;
	
	public DatosRespuestaTopico registrarTopico(RegistroDatosTopico datos) {
		IdNegativo.verificar(datos.idAutor());
		IdNegativo.verificar(datos.idCurso());
		
		validarUsuarioActivo(datos.idAutor());
		validarUsuarioExistente(datos.idAutor());
		validarCursoExistente(datos.idCurso());
		
		var autor = usuarioRepository.findById(datos.idAutor()).get();
		var curso = cursoRepository.findById(datos.idCurso()).get();
		
		topicoRepository.existsByTitulo(datos.titulo());
		
		var topico = new Topico(datos.titulo(), datos.mensaje(), LocalDateTime.now(), autor, curso);
		topicoRepository.save(topico);
		
		return new DatosRespuestaTopico(topico);
	}
	
	public DatosRespuestaTopico actualizarTopico(ActualizarDatosTopico datos) {
		IdNegativo.verificar(datos.id());
		IdNegativo.verificar(datos.idCurso());
		validarTopicoExistente(datos.id());
		validarCursoExistente(datos.idCurso());
		
		var topico = topicoRepository.getReferenceById(datos.id());
		var curso = cursoRepository.findById(datos.id()).get();
		
		validarUsuarioActivo(topico.getAutor().getId());
		
		topico.actualizar(datos.titulo(), datos.mensaje(), datos.status(), curso);
		
		return new DatosRespuestaTopico(topico);
	}
	
	public void eliminarTopico(Long id) {
		IdNegativo.verificar(id);
		validarTopicoExistente(id);
		
		var topico = topicoRepository.getReferenceById(id);
		validarUsuarioActivo(topico.getAutor().getId());
		
		topico.eliminar();
	}
	
	public Page<ListarDatosTopico>listarTopicos(Pageable pageable) {
		var pagina = topicoRepository.findByActiveTrue(pageable).map(ListarDatosTopico::new);
		
		return pagina;
	}
	
	public Object detallesTopico(Long id) {
		Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
		IdNegativo.verificar(id);
		validarTopicoExistente(id);
		
		Topico topico = topicoRepository.getReferenceById(id);
		validarTopicoActivo(topico.getActivo());
		
		if(topico != null) {
			Page<Respuesta> respuesta = respuestaRepository.findByTopicoActivoId(pageable, id);
			
			if(!respuesta.isEmpty()) {
				Page<ListarDatosRespuesta> paginaRespuesta = respuesta.map(response -> new ListarDatosRespuesta(
						response.getId(),
						response.getMensaje(),
						response.getFechaCreacion(),
						response.getAutor().getNombre(),
						response.getSolucion()));
				return new DetallesTopico(
						topico.getId(),
						topico.getTitulo(),
						topico.getMensaje(),
						topico.getFechaCreacion(),
						topico.getStatus(),
						topico.getAutor().getNombre(),
						topico.getCurso().getNombre(),
						paginaRespuesta.getContent());
			}else {
				return new ListarDatosTopico(
						topico.getId(),
						topico.getTitulo(),
						topico.getMensaje(),
						topico.getFechaCreacion(),
						topico.getStatus(),
						topico.getAutor().getNombre(),
						topico.getCurso().getNombre());
			}
		}else {
			return Page.empty();
		}
		
	}
	

	private void validarTopicoActivo(Boolean activo) {
		if(activo == false) {
			throw new ValidacionDeIntegridad("El topico no esta dispible o fue eliminado");
		}
	}

	private void validarTopicoExistente(Long id) {
		if(!topicoRepository.findById(id).isPresent()) {
			throw new ValidacionDeIntegridad("El topico no se encuentra en la base de datos");
		}
		
	}

	private void validarUsuarioActivo(Long id) {
		if(!id.equals(SesionActivaUsuario.idUsuario)) {
			throw new ValidacionDeIntegridad("El usuario que intenta realizar la operacion no es el que inicio la sesion");
		}
		if(!id.equals(SesionActivaUsuario.idUsuario) && !SesionActivaUsuario.username.equals(admin) ) {
			throw new ValidacionDeIntegridad("El usuario que intenta realizar la operacion no es el administrador");
		}
	}
	
	private void validarUsuarioExistente(Long idAutor) {
		if(!usuarioRepository.findById(idAutor).isPresent()) {
			throw new ValidacionDeIntegridad("El usuario no esta registrado en el sistema");
		}
	}

	private void validarCursoExistente(Long idCurso) {
		if(!cursoRepository.findById(idCurso).isPresent()) {
			throw new ValidacionDeIntegridad("El curso no existe, ingrese un nuevo valor");
		}
	}
}
