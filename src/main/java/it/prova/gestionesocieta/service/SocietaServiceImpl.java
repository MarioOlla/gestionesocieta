package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService{
	
	@Autowired
	private SocietaRepository societaRepository;
	@Autowired
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Societa> listAllSocieta() {
		return (List<Societa>) societaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Societa caricaSingolaSocieta(Long id) {
		
		return societaRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Societa societa) {
		societaRepository.save(societa);
		
	}

	@Transactional
	public void inserisciNuova(Societa societa) {
		societaRepository.save(societa);
	}

	@Transactional
	public void rimuovi(Societa societa) {
		societaRepository.delete(societa);
	}

	@Transactional(readOnly = true)
	public List<Societa> findByExample(Societa example) {
		String query = "select s from Societa s where s.id=s.id";
		
		if(StringUtils.isNotEmpty(example.getRagioneSociale()))
			query += " and s.ragionesociale like '%"+example.getRagioneSociale()+"%'";
		if(StringUtils.isNotEmpty(example.getIndirizzo()))
			query += " and s.indirizzo like '%"+example.getIndirizzo()+"%'";
		if(example.getDataFondazione()!=null)
			query += " and s.datacreazione > '"+example.getDataFondazione()+"'";
		
		return entityManager.createQuery(query, Societa.class).getResultList();
	}

}
