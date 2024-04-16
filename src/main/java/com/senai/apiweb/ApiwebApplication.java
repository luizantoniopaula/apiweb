package com.senai.apiweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiwebApplication.class, args);
                
                System.out.printf("\n\n\t\t################################################\n");
                System.out.printf("\t\t##      Aplicação Web para API RestFull       ##\n");
                System.out.printf("\t\t##         com Entidades Relacionadas         ##\n");
                System.out.printf("\t\t##           em banco de dados                ##\n");
                System.out.printf("\t\t##   Acesso: http://localhost:8010/apiweb/    ##\n");
                System.out.printf("\t\t################################################\n");
                System.out.printf("\n\n\n\t\t\tAplicação APIWEB is on The Fly!!!\n");
	}

}
