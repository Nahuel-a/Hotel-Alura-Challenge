package com.latam.alura.dao;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.modelos.Reserva;

public class ReservaDAO {
	private EntityManager em;

	public ReservaDAO(EntityManager em) {
		this.em = em;
	}

	public ReservaDAO() { }

	public Reserva guardar(LocalDate fechaIngreso, LocalDate fechaSalida, double valor, String formaDePago) {
		
		Reserva reserva = new Reserva(fechaIngreso, fechaSalida, valor, formaDePago);
		this.em.persist(reserva);
		return reserva;
	}
	
	public void eliminar(Long idReserva) {
		Reserva reserva = em.find(Reserva.class, idReserva);
		em.remove(reserva);		
	}
	
	public void modificar(Long id, LocalDate fechaIngresoModificada, LocalDate fechaSalidaModificada, Double valor,
			String formaDePago) {
		Reserva reserva = em.find(Reserva.class, id);
		
		reserva.setFechaIngreso(fechaIngresoModificada);
		reserva.setFechaSalida(fechaSalidaModificada);
		
		double valorActualizado = calcularPrecio(fechaIngresoModificada, fechaSalidaModificada);
				
		reserva.setValor(valorActualizado);
		reserva.setFormaDePago(formaDePago);
		
		em.merge(reserva);
	}

	public List<Reserva> listarReservas() {
		String jpql = "SELECT r FROM Reserva r";
		return em.createQuery(jpql, Reserva.class).getResultList();
	}

	public List<Reserva> consultarPorId(String entrada) {
		String jpql  = "SELECT r FROM Reserva r WHERE "
				+ "(YEAR(r.fechaIngreso) = :fecha "
				+ "OR YEAR(r.fechaSalida) = :fecha "
				+ "OR r.valor = :valor "
				+ "OR r.formaDePago LIKE :formaDePago "
				+ "OR CAST(r.id AS string) = :id)";
		
		int fecha = -1;
		double valor = -1;
		String formaDePago = null;
		String idString = null;
		
		try {
		    fecha = Integer.parseInt(entrada);
		} catch (NumberFormatException e) {
			
		}

		try {
			valor = Double.parseDouble(entrada);
		} catch (NumberFormatException e) {
			
		}
		idString = entrada;
		
		return em.createQuery(jpql, Reserva.class)
				.setParameter("fecha", fecha)
				.setParameter("valor", valor)
				.setParameter("formaDePago","%" + entrada + "%")
				.setParameter("id", idString)
				.getResultList();
	}
	
	public double calcularPrecio(LocalDate fechaEntrada, LocalDate fechaSalida) {
		double precioPorDia = 7500;
		long diferenciaDias = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);

		double precioReserva = diferenciaDias * precioPorDia;
		return precioReserva;
	}
}




