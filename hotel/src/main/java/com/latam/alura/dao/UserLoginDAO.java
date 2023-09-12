package com.latam.alura.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.mindrot.jbcrypt.BCrypt;

import com.latam.alura.modelos.User;


public class UserLoginDAO {
	private EntityManager em;
	
	public UserLoginDAO(EntityManager em){
		this.em = em;
	}
		
	public UserLoginDAO() {	}

	public void eliminar(User user) {
		em.remove(user);
	}
	
	public void guardar(String usuario, String clave) {
		
		String passwordSecuredHash = BCrypt.hashpw(clave, BCrypt.gensalt());
		
		User user = new User(usuario, passwordSecuredHash);
		em.getTransaction().begin();
		this.em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public boolean validarUsuario(String usuario, String clave) {
		String jpql = "SELECT p FROM User p WHERE p.username = :usuario";
		TypedQuery<User> query = em.createQuery(jpql, User.class).setParameter("usuario", usuario);
		
		List<User> users = query.getResultList();
		
		if(!users.isEmpty()) {
			User user = users.get(0);
			return validarClave(clave, user.getPassword());
		}
		return false;
	}
	
	private boolean validarClave(String password, String passwordSecured) {
		
		return BCrypt.checkpw(password, passwordSecured);
	}
}