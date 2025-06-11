package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório responsável por realizar operações de acesso a dados para a entidade Disciplina.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a definição de consultas personalizadas.
 */
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    /**
     * Verifica se existe alguma relação entre filmes e a disciplina informada.
     * Utiliza consulta nativa para verificar a existência na tabela de relacionamento.
     *
     * @param disciplinaId ID da disciplina a ser verificada.
     * @return true se existir relação, caso contrário false.
     */
    @Query(value = "SELECT COUNT(*) > 0 FROM filme_disciplinas WHERE disciplinas_id = :disciplinaId", nativeQuery = true)
    boolean existsByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);

    /**
     * Busca todas as disciplinas ordenadas pelo nome em ordem crescente.
     *
     * @return Lista de disciplinas ordenadas por nome.
     */
    List<Disciplina> findAllByOrderByNomeAsc();

    /**
     * Verifica se existe alguma disciplina com o nome fornecido.
     *
     * @param nome Nome da disciplina a ser verificado.
     * @return true se existir uma disciplina com o nome fornecido, caso contrário false.
     */
    boolean existsByNome(String nome);

    /**
     * Busca disciplinas cujo nome contenha o texto fornecido, ignorando maiúsculas e minúsculas.
     * A consulta utiliza JPQL para realizar a busca com o operador LIKE.
     *
     * @param nome Texto a ser pesquisado no nome das disciplinas.
     * @return Lista de disciplinas que correspondem ao critério de busca.
     */
    @Query("SELECT d FROM Disciplina d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Disciplina> buscarPorNome(@Param("nome") String nome);
}