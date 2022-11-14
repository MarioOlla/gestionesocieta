package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaService {
	
	public List<Societa> listAllSocieta();
	
	public Societa caricaSingolaSocieta(Long id);
	
	public Societa caricaSingolaSocietaFetch(Long id);
	
	public void aggiorna(Societa societa);
	
	public void inserisciNuova(Societa societa);
	
	public void rimuovi(Societa societa);
	
	public List<Societa> findByExample(Societa example);
}
