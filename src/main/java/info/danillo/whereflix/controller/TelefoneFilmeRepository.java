package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositório responsável por realizar operações de acesso a dados para a entidade TelefoneFilme.
 * Extende JpaRepository para fornecer métodos padrão de CRUD e permite a definição de consultas personalizadas.
 */
public interface TelefoneFilmeRepository extends JpaRepository<TelefoneFilme, Integer> {

    /**
     * Busca todos os telefones associados a um filme específico.
     *
     * @param filmeId ID do filme cujos telefones serão buscados.
     * @return Lista de telefones associados ao filme.
     */
    @Query("SELECT t FROM TelefoneFilme t WHERE t.filme.id = :filmeId")
    List<TelefoneFilme> findByFilmeId(@Param("filmeId") Integer filmeId);

    /**
     * Verifica se existe um telefone com o número fornecido associado a um filme.
     *
     * @param numero Número de telefone a ser verificado.
     * @return true se o telefone existir, caso contrário false.
     */
    boolean existsByNumero(String numero);
}
