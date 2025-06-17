package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


/**
 * Repositório responsável por realizar operações de acesso a dados para a entidade Streaming.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a definição de consultas personalizadas.
 */
public interface StreamingRepository extends JpaRepository<Streaming, Integer>, CrudRepository<Streaming, Integer> {

    /**
     * Verifica se existe alguma relação entre filmes e a streaming informada.
     * Utiliza consulta nativa para verificar a existência na tabela de relacionamento.
     *
     * @param streamingId ID da streaming a ser verificada.
     * @return true se existir relação, caso contrário false.
     */
    @Query(value = "SELECT COUNT(*) > 0 FROM filme_streamings WHERE streamings_id = :streamingId", nativeQuery = true)
    boolean existsByStreamingId(@Param("streamingId") Integer streamingId);

    /**
     * Busca todas as streamings ordenadas pelo nome em ordem crescente.
     *
     * @return Lista de streamings ordenadas por nome.
     */
    List<Streaming> findAllByOrderByNomeAsc();

    /**
     * Verifica se existe alguma streaming com o nome fornecido.
     *
     * @param nome Nome da streaming a ser verificado.
     * @return true se existir uma streaming com o nome fornecido, caso contrário false.
     */
    boolean existsByNome(String nome);

    /**
     * Busca streamings cujo nome contenha o texto fornecido, ignorando maiúsculas e minúsculas.
     * A consulta utiliza JPQL para realizar a busca com o operador LIKE.
     *
     * @param nome Texto a ser pesquisado no nome das streamings.
     * @return Lista de streamings que correspondem ao critério de busca.
     */
    @Query("SELECT d FROM Streaming d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Streaming> buscarPorNome(@Param("nome") String nome);

    Streaming findByNome(String nome);
}