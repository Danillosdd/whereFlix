package info.danillo.whereflix.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WhereflixApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhereflixApplication.class, args);
    }

    @Bean
    public org.springframework.boot.CommandLineRunner loadTipos(TipoRepository tipoRepository) {
        return args -> {
            inserirOuAtualizarTipo(tipoRepository, "Filme");
            inserirOuAtualizarTipo(tipoRepository, "Série");
            inserirOuAtualizarTipo(tipoRepository, "TV Show");
        };
    }

    private void inserirOuAtualizarTipo(TipoRepository tipoRepository, String nome) {
        Tipo tipo = tipoRepository.findByNomeIgnoreCase(nome).orElse(null);
        if (tipo == null) {
            tipo = new Tipo();
            tipo.setNome(nome);
        } else {
            tipo.setNome(nome); // Atualiza o nome se quiser
        }
        tipoRepository.save(tipo);
    }
}
