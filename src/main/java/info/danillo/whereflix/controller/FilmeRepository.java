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
     * Busca filmes cujo título contenha o texto fornecido, ignorando maiúsculas e
     * minúsculas.
     * A consulta utiliza JPQL para realizar a busca com o operador LIKE.
     *
     * @param titulo Texto a ser pesquisado no título dos filmes.
     * @return Lista de filmes que correspondem ao critério de busca.
     */
    @Query("SELECT a FROM Filme a WHERE LOWER(a.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Filme> buscarPorTitulo(@Param("titulo") String titulo);

    boolean existsByTituloIgnoreCase(String titulo);
}
