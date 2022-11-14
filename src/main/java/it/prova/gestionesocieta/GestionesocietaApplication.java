package it.prova.gestionesocieta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionesocietaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(GestionesocietaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("################ START   #################");
		System.out.println("################ eseguo i test  #################");
		
		
		
		System.out.println("################ FINE   #################");
	}

}
