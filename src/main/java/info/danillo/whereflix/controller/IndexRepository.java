package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositório responsável por realizar operações de acesso a dados para a
 * entidade filme.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a
 * definição de consultas personalizadas.
 */
public interface IndexRepository extends JpaRepository<Filme, Integer> {

    // Lançamentos
    List<Filme> findTop4ByOrderByAnoDesc();
    List<Filme> findTop4ByTipoNomeOrderByAnoDesc(String tipo);

    // Bem avaliados
    List<Filme> findTop8ByOrderByAvaliacaoDesc();

    // 4 filmes aleatórios (usando JPQL)
    @Query(value = "SELECT * FROM filme ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
    List<Filme> buscar4Aleatorios();
}
