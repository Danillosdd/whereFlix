package br.ueg.desenvolvimento.web.projeto_danillo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * Responsável por inicializar e configurar o contexto da aplicação.
 */
@SpringBootApplication
public class ProjetoDanilloApplication {

    /**
     * Método principal que inicia a aplicação.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        // Inicializa a aplicação Spring Boot
        SpringApplication.run(ProjetoDanilloApplication.class, args);
    }
}
