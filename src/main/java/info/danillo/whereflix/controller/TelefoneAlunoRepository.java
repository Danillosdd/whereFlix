package br.ueg.desenvolvimento.web.projeto_danillo.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório responsável por realizar operações de acesso a dados para a entidade TelefoneAluno.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a definição de consultas personalizadas.
 */
public interface TelefoneAlunoRepository extends JpaRepository<TelefoneAluno, Integer> {

    /**
     * Busca todos os telefones associados a um aluno específico.
     *
     * @param alunoId ID do aluno cujos telefones serão buscados.
     * @return Lista de telefones associados ao aluno.
     */
    @Query("SELECT t FROM TelefoneAluno t WHERE t.aluno.id = :alunoId")
    List<TelefoneAluno> findByAlunoId(@Param("alunoId") Integer alunoId);

    /**
     * Verifica se existe um telefone com o número fornecido associado a um aluno.
     *
     * @param numero Número de telefone a ser verificado.
     * @return true se o telefone existir, caso contrário false.
     */
    boolean existsByNumero(String numero);
}
