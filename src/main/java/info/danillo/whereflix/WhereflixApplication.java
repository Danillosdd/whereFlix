package info.danillo.whereflix;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import info.danillo.whereflix.controller.Categoria;
import info.danillo.whereflix.controller.CategoriaRepository;
import info.danillo.whereflix.controller.Qualidade;
import info.danillo.whereflix.controller.QualidadeRepository;
import info.danillo.whereflix.controller.Tipo;
import info.danillo.whereflix.controller.TipoRepository;

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
            inserirOuAtualizarTipo(tipoRepository, "Show");
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

    @Bean
    public org.springframework.boot.CommandLineRunner loadCategorias(CategoriaRepository categoriaRepository) {
        return args -> {
            inserirOuAtualizarCategoria(categoriaRepository, "Ação");
            inserirOuAtualizarCategoria(categoriaRepository, "Animação");
            inserirOuAtualizarCategoria(categoriaRepository, "Aventura");
            inserirOuAtualizarCategoria(categoriaRepository, "Biografia");
            inserirOuAtualizarCategoria(categoriaRepository, "Comédia");
            inserirOuAtualizarCategoria(categoriaRepository, "Drama");
            inserirOuAtualizarCategoria(categoriaRepository, "Documentário");
            inserirOuAtualizarCategoria(categoriaRepository, "Fantasia");
            inserirOuAtualizarCategoria(categoriaRepository, "Fatos Reais");
            inserirOuAtualizarCategoria(categoriaRepository, "Ficção Científica");
            inserirOuAtualizarCategoria(categoriaRepository, "Romance");
            inserirOuAtualizarCategoria(categoriaRepository, "Super-heróis");
            inserirOuAtualizarCategoria(categoriaRepository, "Suspense");
            inserirOuAtualizarCategoria(categoriaRepository, "Terror");
        };
    }

    private void inserirOuAtualizarCategoria(CategoriaRepository categoriaRepository, String nome) {
        Categoria categoria = categoriaRepository.findByNomeIgnoreCase(nome).orElse(null);
        if (categoria == null) {
            categoria = new Categoria();
            categoria.setNome(nome);
        } else {
            categoria.setNome(nome);
        }
        categoriaRepository.save(categoria);
    }

    @Bean
    public org.springframework.boot.CommandLineRunner loadQualidades(QualidadeRepository qualidadeRepository) {
        return args -> {
            inserirOuAtualizarQualidade(qualidadeRepository, "HD");
            inserirOuAtualizarQualidade(qualidadeRepository, "Full HD");
            inserirOuAtualizarQualidade(qualidadeRepository, "4K");
        };
    }

    private void inserirOuAtualizarQualidade(QualidadeRepository qualidadeRepository, String nome) {
        Qualidade qualidade = qualidadeRepository.findAll().stream()
                .filter(q -> q.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
        if (qualidade == null) {
            qualidade = new Qualidade();
            qualidade.setNome(nome);
            qualidadeRepository.save(qualidade);
        }
    }

    public class StartupCheck {
        public void checkUploadFolder() {
            File folder = new File("upload");
            if (folder.exists() && folder.isDirectory()) {
                for (File file : folder.listFiles()) {
                    System.out.println("Arquivo encontrado: " + file.getName());
                }
            } else {
                System.out.println("Pasta 'upload' não encontrada.");
            }
        }
    }
}
