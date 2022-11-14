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
			dataFondazione = new SimpleDateFormat("yyyy-MM-dd").parse("2002-03-22");
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
			dataFondazione = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
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
			dataAssunzione = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-02");
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

	public void testRimuoviSocieta() {
		List<Societa> societaSuDB = societaService.listAllSocieta();
		if (societaSuDB.isEmpty())
			throw new RuntimeException("testRimuoviSocieta.....failed, il DB e' gia vuoto....");

		try {
			societaService.rimuovi(societaSuDB.get(0));
		} catch (SocietaConAncoraDipendentiException e) {
			System.out.println("testRimuoviSocieta..... eccezione lanciata come atteso:" + e.getMessage());
		}

		Dipendente toRemove = societaService.caricaSingolaSocietaFetch(societaSuDB.get(0).getId()).getDipendenti()
				.stream().findFirst().orElse(null);

		dipendenteService.rimuovi(toRemove);

		societaService.rimuovi(societaSuDB.get(0));
		
		if(!societaService.listAllSocieta().isEmpty())
			throw new RuntimeException("testRimuoviSocieta.....failed");
		System.out.println("testRimuoviSocieta.....OK");
	}
}
