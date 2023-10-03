package com.alura.modelo.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
	
	Boolean existsByTitulo(String titulo);
	
	Page<Topico> findByActiveTrue(Pageable pageable);
}
