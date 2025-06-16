package info.danillo.whereflix.controller;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QualidadeRepository extends JpaRepository<Qualidade, Integer> {
    Optional<Qualidade> findByNomeIgnoreCase(String nome);
}