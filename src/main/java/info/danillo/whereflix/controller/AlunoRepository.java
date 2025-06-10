package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório responsável por realizar operações de acesso a dados para a entidade Aluno.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a definição de consultas personalizadas.
 */
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    /**
     * Busca alunos cujo nome contenha o texto fornecido, ignorando maiúsculas e minúsculas.
     * A consulta utiliza JPQL para realizar a busca com o operador LIKE.
     *
     * @param nome Texto a ser pesquisado no nome dos alunos.
     * @return Lista de alunos que correspondem ao critério de busca.
     */
    @Query("SELECT a FROM Aluno a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Aluno> buscarPorNome(@Param("nome") String nome);

    /**
     * Verifica se existe algum aluno com a matrícula fornecida no banco de dados.
     *
     * @param matricula Número de matrícula a ser verificado.
     * @return true se existir um aluno com a matrícula fornecida, caso contrário false.
     */
    boolean existsByMatricula(Integer matricula);

    /**
     * Busca e retorna um aluno com a matrícula fornecida no banco de dados.
     *
     * @param matricula Número de matrícula do aluno a ser buscado.
     * @return Objeto Aluno correspondente à matrícula fornecida, ou null se não encontrado.
     */
    Aluno findByMatricula(Integer matricula);
}
