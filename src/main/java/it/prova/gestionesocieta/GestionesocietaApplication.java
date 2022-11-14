package it.prova.gestionesocieta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionesocieta.service.TestFunzionalitaService;

@SpringBootApplication
public class GestionesocietaApplication implements CommandLineRunner{
	
	@Autowired
	private TestFunzionalitaService testFunzionalitaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionesocietaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("################ START   #################");
		System.out.println("################ eseguo i test  #################");
		
		testFunzionalitaService.testInserisciSocieta();
		testFunzionalitaService.testFindByExampleSocieta();
		testFunzionalitaService.testInserisciDipendente();
		testFunzionalitaService.testAggiornaDipendente();
		testFunzionalitaService.testFindSocietaConAlmenoUnDipendenteRalSopra30000();
		testFunzionalitaService.testIlPiuAnzianoTraIDipendentiDiSocietaFondateDal1990();
		testFunzionalitaService.testRimuoviSocieta();
		
		System.out.println("################ FINE   #################");
	}

}
