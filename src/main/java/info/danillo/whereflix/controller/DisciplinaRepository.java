package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    @Query(value = "SELECT COUNT(*) > 0 FROM filme_disciplinas WHERE disciplinas_id = :disciplinaId", nativeQuery = true)
    boolean existsByDisciplinaId(@Param("disciplinaId") Integer disciplinaId);

    List<Disciplina> findAllByOrderByNomeAsc();

    boolean existsByNome(String nome);

}