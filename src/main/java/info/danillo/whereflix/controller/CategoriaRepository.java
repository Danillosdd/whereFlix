package info.danillo.whereflix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNomeIgnoreCase(String nome);
    List<Categoria> findAllByOrderByNomeAsc();
}