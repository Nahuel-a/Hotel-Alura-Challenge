package com.latam.alura.controller;

import javax.persistence.EntityManager;

import com.latam.alura.dao.UserLoginDAO;
import com.latam.alura.utils.JPAUtils;

public class UserController {
	private EntityManager em;
	private UserLoginDAO userDao;
	
	public UserController() {
		this.em = JPAUtils.getEntityManager();
		this.userDao = new UserLoginDAO(em);
	}
	
	public void guardar(String usuario, String clave) {
		userDao.guardar(usuario, clave);
	}
	
	public boolean validarUsuario(String usuario, String clave) {
		return userDao.validarUsuario(usuario, clave);
	}
}
