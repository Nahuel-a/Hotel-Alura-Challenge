package com.alura.modelo.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	UserDetails findByUsername(String username);

	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
}
