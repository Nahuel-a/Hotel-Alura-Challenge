package com.alura.modelo.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	
	Page<Respuesta> findByTopicoId(Pageable pageable, Long id);
	
	Page<Respuesta> findByTopicoActivoId(Pageable pageable, Long id);
}
