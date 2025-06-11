package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório responsável por realizar operações de acesso a dados para a
 * entidade filme.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a
 * definição de consultas personalizadas.
 */
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    /**
     * Busca filmes cujo nome contenha o texto fornecido, ignorando maiúsculas e
     * minúsculas.
     * A consulta utiliza JPQL para realizar a busca com o operador LIKE.
     *
     * @param nome Texto a ser pesquisado no nome dos filmes.
     * @return Lista de filmes que correspondem ao critério de busca.
     */
    @Query("SELECT a FROM Filme a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Filme> buscarPorNome(@Param("nome") String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
