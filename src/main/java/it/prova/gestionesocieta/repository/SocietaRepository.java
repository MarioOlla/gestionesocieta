package it.prova.gestionesocieta.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaRepository extends CrudRepository<Societa, Long>,QueryByExampleExecutor <Societa> {
	
	@Query("from Societa s left join s.dipendenti where s.id = ?1")
	@EntityGraph(attributePaths = {"dipendenti"})
	public Societa findByIdEager(Long id);
	
}
