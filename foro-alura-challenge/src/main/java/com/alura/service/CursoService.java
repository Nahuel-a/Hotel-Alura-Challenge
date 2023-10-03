package com.alura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alura.infra.errores.IdNegativo;
import com.alura.infra.errores.ValidacionDeIntegridad;
import com.alura.modelo.curso.ActializarDatosCurso;
import com.alura.modelo.curso.Curso;
import com.alura.modelo.curso.CursoRepository;
import com.alura.modelo.curso.DatosRespuestaCurso;
import com.alura.modelo.curso.RegistroDatosCurso;
import com.alura.modelo.usuario.SesionActivaUsuario;


@Service
public class CursoService {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Value("${forum.usur.username}")
	private String admin;
	
	public DatosRespuestaCurso registrarCurso(RegistroDatosCurso datos) {
		validarUsuarioActivo();
		var curso = new Curso(datos.nombre(), datos.categoria());
		cursoRepository.save(curso);
		return new DatosRespuestaCurso(curso);
	}

	public DatosRespuestaCurso actualizarCurso(ActializarDatosCurso datos) {
		validarUsuarioActivo();
		IdNegativo.verificar(datos.id());
		ValidarCursoExistentePorId(datos.id());
		ValidarCursoExistentePorNombre(datos.nombre());
		
		var curso = cursoRepository.getReferenceById(datos.id());
		curso.actualizar(datos.nombre(), datos.categoria());
		return new DatosRespuestaCurso(curso);
	}

	public void eliminar(Long id) {
		validarUsuarioActivo();
		IdNegativo.verificar(id);
		ValidarCursoExistentePorId(id);
		
		var curso = cursoRepository.getReferenceById(id);
		curso.eliminar();
	}

	private void ValidarCursoExistentePorNombre(String nombre) {
		if (cursoRepository.findByNombre(nombre)) {
			throw new ValidacionDeIntegridad("El curso ya fue registrado");
		}
	}
	
	private void ValidarCursoExistentePorId(Long id) {
		if(!cursoRepository.existsById(id)) {
			throw new ValidacionDeIntegridad("El curso no se encontro, verifique los datos ingresados");
		}
	}
	
	private void validarUsuarioActivo() {
		if(!SesionActivaUsuario.username.equals(admin)) {
			throw new ValidacionDeIntegridad("El unico que puede eliminar, modificar y crear cursos es el administrador");
		}
	}
}
