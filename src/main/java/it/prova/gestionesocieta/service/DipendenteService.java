package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {

	public List<Dipendente> listAllDipendenti();
	
	public Dipendente caricaSingoloDipendente(Long id);
	
	public void aggiorna(Dipendente dipendente);
	
	public void inserisciNuovo(Dipendente dipendente);
	
	public void rimuovi(Dipendente dipendente);
	
	public List<Dipendente> findByExample(Dipendente example);
	
	public Dipendente ilPiuAnzianoTraIDipendentiDiSocietaFondateDal1990();
}
