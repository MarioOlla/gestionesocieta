package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.SocietaConAncoraDipendentiException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

@Service
public class TestFunzionalitaService {
	@Autowired
	private SocietaService societaService;
	@Autowired
	private DipendenteService dipendenteService;

	public void testInserisciSocieta() {
		Date dataFondazione = new Date();
		try {
			dataFondazione = new SimpleDateFormat("yyyy-MM-dd").parse("1985-03-22");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		societaService.inserisciNuova(new Societa("Pippo costruzioni", "via Macedonia 3", dataFondazione));

		if (societaService.listAllSocieta().isEmpty())
			throw new RuntimeException("testInserisciSocieta.....failed, inserimento non avvenuto.");
		System.out.println("testInserisciSocieta.....OK");
	}

	public void testFindByExampleSocieta() {
		Date dataFondazione = new Date();
		try {
			dataFondazione = new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Societa> result;
		result = societaService.findByExample(new Societa("ippo", null, dataFondazione));
		if (result.isEmpty())
			throw new RuntimeException(
					"testFindByExampleSocieta.....failed, la ricerca non ha prodotto i risultati attesi");
		System.out.println("testFindByExampleSocieta.....OK");
	}

	public void testInserisciDipendente() {
		Date dataAssunzione = new Date();
		try {
			dataAssunzione = new SimpleDateFormat("yyyy-MM-dd").parse("1998-04-02");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Societa> societaSuDb = societaService.listAllSocieta();
		if (societaSuDb.isEmpty())
			throw new RuntimeException(
					"testInserisciDipendente.....failed, non ci sono societa' sul db, impossibile inserire Dipendente.");
		dipendenteService
				.inserisciNuovo(new Dipendente("Franco", "Di Maria", dataAssunzione, 26000, societaSuDb.get(0)));
		if (dipendenteService.listAllDipendenti().isEmpty())
			throw new RuntimeException("testInserisciDipendente.....failed, inserimento non avvenuto.");
		System.out.println("testInserisciDipendente.....OK");
	}
	
	public void testAggiornaDipendente() {
		List<Dipendente> dipendentiSuDb = dipendenteService.listAllDipendenti();
		if(dipendentiSuDb.isEmpty())
			throw new RuntimeException("errore, il DB sembra essere vuoto...");
		dipendentiSuDb.get(0).setRal(32000);
		dipendentiSuDb.get(0).setCognome("Berulli");
		Long idDaAggiornare = dipendentiSuDb.get(0).getId();
		
		dipendenteService.aggiorna(dipendentiSuDb.get(0));
		
		if(!dipendenteService.caricaSingoloDipendente(idDaAggiornare).getCognome().equals("Berulli")) {
			System.out.println(dipendenteService.caricaSingoloDipendente(idDaAggiornare).getCognome());
			throw new RuntimeException("testAggiornaDipendente......failed, i dati non sono aggiornati.");
		}
		System.out.println("testAggiornaDipendente.....OK");
	}
	
	public void testFindSocietaConAlmenoUnDipendenteRalSopra30000() {
		List<Societa> societaSuDB = societaService.listAllSocieta();
		if (societaSuDB.isEmpty())
			throw new RuntimeException("testFindSocietaConAlmenoUnDipendenteRalSopra30000.....failed, il DB sembra essere vuoto....");
		
		if(societaService.tutteSocietaConDipendentiRalMaggioreDi30000().isEmpty())
			throw new RuntimeException("testFindSocietaConAlmenoUnDipendenteRalSopra30000.....failed,la ricerca non ha prodotto i risultati attesi.");
		System.out.println("testFindSocietaConAlmenoUnDipendenteRalSopra30000.....OK");
	}
	
	public void testRimuoviSocieta() {
		List<Societa> societaSuDB = societaService.listAllSocieta();
		if (societaSuDB.isEmpty())
			throw new RuntimeException("testRimuoviSocieta.....failed, il DB e' gia vuoto....");

		try {
			societaService.rimuovi(societaSuDB.get(0));
		} catch (SocietaConAncoraDipendentiException e) {
			System.out.println("testRimuoviSocieta..... eccezione lanciata come atteso:" + e.getMessage());
		}

		 societaService.caricaSingolaSocietaFetch(societaSuDB.get(0).getId()).getDipendenti()
				.stream().forEach(dip -> {dipendenteService.rimuovi(dip);});

		societaService.rimuovi(societaSuDB.get(0));
		
		if(!societaService.listAllSocieta().isEmpty())
			throw new RuntimeException("testRimuoviSocieta.....failed");
		System.out.println("testRimuoviSocieta.....OK");
	}
	

}
