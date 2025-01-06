package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    
    List<Categoria> findByNombreCategoriaContaining(String nombreCategoria);

    boolean existsByNombreCategoria(String nombreCategoria);

    void deleteById(Long id);
}
