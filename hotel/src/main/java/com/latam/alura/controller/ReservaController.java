package com.latam.alura.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JTable;

import com.latam.alura.dao.ReservaDAO;
import com.latam.alura.modelos.Reserva;
import com.latam.alura.utils.JPAUtils;

public class ReservaController {
	private EntityManager em;
	private ReservaDAO reservaDao;
	private EntityTransaction transaction;
	
	private static final int COL_ID = 0;
	private static final int COL_FECHA_INGRESO = 1;
	private static final int COL_FECHA_SALIDA = 2;
	private static final int COL_VALOR = 3;
	private static final int COL_FORMA_PAGO = 4;

	public ReservaController() {
		this.em = JPAUtils.getEntityManager();
		this.reservaDao = new ReservaDAO(em);
		this.transaction = em.getTransaction();
	}
	public void cerrarEntity() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
	
	public Reserva guardar(LocalDate fechaIngreso, LocalDate fechaSalida, double valor, String formaDePago) {
		try {
			try {
				if(!transaction.isActive()) {
					transaction.begin();
				}
				
				Reserva reserva = reservaDao.guardar(fechaIngreso, fechaSalida, valor, formaDePago);
				transaction.commit();
				return reserva;
				
			}catch(Exception e) {
				if(transaction.isActive()) {
					transaction.rollback();
				}
				throw e;
			}
		}finally {
			if (em.isOpen()) {
                em.close();
            }
		}
	}
	
	public void eliminar(Long idReserva) {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			reservaDao.eliminar(idReserva);
			transaction.commit();
			
		}catch(Exception e) {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
	}
	
	public void modificar(JTable tbReservas) {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			Integer filaSeleccionada = tbReservas.getSelectedRow();
			Long id = (Long) tbReservas.getValueAt(filaSeleccionada, COL_ID);
			String fechaIngreso = tbReservas.getValueAt(filaSeleccionada, COL_FECHA_INGRESO).toString();
			String fechaSalida =  tbReservas.getValueAt(filaSeleccionada, COL_FECHA_SALIDA).toString();			
			Double valor = Double.parseDouble(tbReservas.getValueAt(filaSeleccionada, COL_VALOR).toString());
			String formaDePago =  tbReservas.getValueAt(filaSeleccionada, COL_FORMA_PAGO).toString();
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fechaIngresoModificada = LocalDate.parse(fechaIngreso, dtf);
			LocalDate fechaSalidaModificada = LocalDate.parse(fechaSalida, dtf);

			reservaDao.modificar(id, fechaIngresoModificada,fechaSalidaModificada, valor, formaDePago);
			transaction.commit();
			
		}catch(Exception e) {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
		
	}

	public Object[] listar() {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			List<Reserva> reservas = reservaDao.listarReservas();
			Object[] modeloTbReserva = crearModelo(reservas);
			
			return modeloTbReserva;
		}catch(Exception e) {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
	}
	
	public Object[] consultarPorId(String id) {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			List<Reserva> reservas = reservaDao.consultarPorId(id);
			Object[] modeloTbReserva = crearModelo(reservas);
			
			return modeloTbReserva;
		}catch(Exception e) {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
	}
	
	private Object[] crearModelo(List<Reserva> reservas) {
		Object[] modeloTbReserva = new Object[reservas.size()];
		int i= 0;
		
		for(Reserva reserva : reservas) {
			modeloTbReserva[i] = new Object[] {reserva.getId(), reserva.getFechaIngreso(), 
					reserva.getFechaSalida(), reserva.getValor(), reserva.getFormaDePago()
					};
			i++;
		}
		return modeloTbReserva;
	}
	

}
