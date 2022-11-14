package it.prova.gestionesocieta.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>,QueryByExampleExecutor <Dipendente>{
	
	@Query(" from Dipendente d inner join fetch d.societa s where '1990-01-01' > s.dataFondazione order by DATEDIFF( d.dataAssunzione, ?1)")
	public Dipendente theDipendenteWithMostTimeInSocietaFoundedBefore1990(Date currentDate);
}
