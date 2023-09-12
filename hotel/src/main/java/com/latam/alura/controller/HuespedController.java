package com.latam.alura.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JTable;

import com.latam.alura.dao.HuespedDAO;
import com.latam.alura.modelos.Huesped;
import com.latam.alura.modelos.Reserva;
import com.latam.alura.utils.JPAUtils;

public class HuespedController {
	private EntityManager em;
	private HuespedDAO huespedDao;
	private EntityTransaction transaction;
	
	private static final int COL_ID = 0;
	private static final int COL_NOMBRE = 1;
	private static final int COL_APELLIDO = 2;
	private static final int COL_FECHA_NACIMIENTO = 3;
	private static final int COL_TELEFONO = 4;
	private static final int COL_NACIONALIDAD = 5;
	

	public HuespedController() {
		this.em = JPAUtils.getEntityManager();
		this.huespedDao = new HuespedDAO(em);
		this.transaction = em.getTransaction();
	}

	public void cerrarEntity() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

	public void guardar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Reserva reserva) {
		try {
			try {
				if(!transaction.isActive()) {
					transaction.begin();
				}
				huespedDao.guardar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, reserva);
				transaction.commit();
				
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
	
	public void eliminar(Long idHuesped) {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			huespedDao.eliminar(idHuesped);
			transaction.commit();
			
		}catch(Exception e) {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
		
	}
	
	public void modificar(JTable tbHuespedes) {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			Integer filaSeleccionada = tbHuespedes.getSelectedRow();
			Long id = (Long) tbHuespedes.getValueAt(filaSeleccionada, COL_ID);
			String nombre = tbHuespedes.getValueAt(filaSeleccionada, COL_NOMBRE).toString();
			String apellido = tbHuespedes.getValueAt(filaSeleccionada, COL_APELLIDO).toString();
			String fechaNacimiento = tbHuespedes.getValueAt(filaSeleccionada, COL_FECHA_NACIMIENTO).toString();
			String telefono = tbHuespedes.getValueAt(filaSeleccionada, COL_TELEFONO).toString();
			String nacionalidad = tbHuespedes.getValueAt(filaSeleccionada, COL_NACIONALIDAD).toString();
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fechaNacimientoModificado = LocalDate.parse(fechaNacimiento, dtf);
			
			huespedDao.modificar(id, nombre, apellido, fechaNacimientoModificado, telefono, nacionalidad);
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
			List<Huesped> huespedes = huespedDao.listarHuespedes();
			Object[] modeloTbHuesped = crearModelo(huespedes);

			return modeloTbHuesped;
		}catch(Exception e) {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
	}

	public Object[] consultaPorApellido(String apellido) {
		try {
			if(!transaction.isActive()) {
				transaction.begin();
			}
			
			List<Huesped> huespedes = huespedDao.consultaPorApellido(apellido);
			Object[] modeloTbHuesped = crearModelo(huespedes);

			return modeloTbHuesped;
		}catch(Exception e){
			if(transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
	}

	private Object[] crearModelo(List<Huesped> huespedes) {
		Object[] modeloTbHuesped = new Object[huespedes.size()];
		int i = 0;

		for (Huesped huesped : huespedes) {
			modeloTbHuesped[i] = new Object[] { huesped.getId(), huesped.getNombre(), huesped.getApellido(),
					huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
					huesped.getReserva().getId() };
			i++;
		}
		return modeloTbHuesped;
	}
}
