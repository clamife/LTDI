package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Método para buscar categorías por nombre (opcional)
    List<Categoria> findByNombreCategoriaContaining(String nombreCategoria);

    // Método para buscar por descripción (opcional)
    List<Categoria> findByDescripcionCategoriaContaining(String descripcionCategoria);

    boolean existsByNombreCategoria(String nombreCategoria);

    void deleteById(Long id);
}
