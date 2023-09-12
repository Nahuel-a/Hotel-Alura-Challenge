package com.latam.alura.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.modelos.Huesped;
import com.latam.alura.modelos.Reserva;

public class HuespedDAO {
	private EntityManager em;

	public HuespedDAO(EntityManager em) {
		this.em = em;
	}

	public HuespedDAO() {
	}

	public void guardar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Reserva reserva) {
		Huesped huesped = new Huesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, reserva);
		this.em.persist(huesped);

	}
	
	public void eliminar(Long idHuesped) {
		Huesped huesped = em.find(Huesped.class, idHuesped);
		em.remove(huesped);
	}
	
	public void modificar(Long id, String nombre, String apellido, LocalDate fechaNacimientoModificado, String telefono,
			String nacionalidad) {
		Huesped huesped = em.find(Huesped.class, id);
		
		huesped.setNombre(nombre);
		huesped.setApellido(apellido);
		huesped.setFechaNacimiento(fechaNacimientoModificado);
		huesped.setTelefono(telefono);
		huesped.setNacionalidad(nacionalidad);
		
		em.merge(huesped);
		
	}

	public List<Huesped> listarHuespedes() {
		String jpql = "SELECT h FROM Huesped h";
		return em.createQuery(jpql, Huesped.class).getResultList();

	}

	public List<Huesped> consultaPorApellido(String apellido) {
		String jpql = "SELECT h FROM Huesped h WHERE h.apellido LIKE :apellido "
				+ "OR h.id LIKE :apellido "
				+ "OR h.nombre LIKE :apellido " 
				+ "OR h.nacionalidad LIKE :apellido " 
				+ "OR h.telefono LIKE :apellido";
		return em.createQuery(jpql, Huesped.class)
				.setParameter("apellido", "%" + apellido + "%")
				.getResultList();
	}
	
	public String validarTelefono(String telefono) {
		if (!(telefono.length() >= 7 && telefono.length() <= 15) || !telefono.matches("[0-9-]+")) {
			throw new IllegalArgumentException("El número de teléfono no cumple con los parametros deseados");
		}

		return telefono;
	}
}