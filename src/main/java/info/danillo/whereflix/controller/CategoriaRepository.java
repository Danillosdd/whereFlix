package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findAllByOrderByNomeAsc();
}