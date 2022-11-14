package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {
	
	@Autowired
	private DipendenteRepository dipendenteRepository;
	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Dipendente> listAllDipendenti() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendente(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);

	}

	@Transactional
	public void inserisciNuovo(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}

	@Transactional
	public void rimuovi(Dipendente dipendente) {
		dipendenteRepository.delete(dipendente);
	}

	@Transactional(readOnly = true)
	public List<Dipendente> findByExample(Dipendente example) {
		String query = "select d from Dipendente d where d.id=d.id";
		
		if(StringUtils.isNoneEmpty(example.getNome()))
			query += " and d.nome like '%"+example.getNome()+"%'";
		if(StringUtils.isNoneEmpty(example.getCognome()))
			query += " and d.cognome like '%"+example.getCognome()+"%'";
		if(example.getDataAssunzione()!= null)
			query += " and d.dataAssunzione > "+example.getDataAssunzione().toInstant();
		if(example.getRal()!= null)
			query += " and d.ral > "+example.getRal();
		
		return entityManager.createQuery(query,Dipendente.class).getResultList();
	}

}
