package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public List<Categoria> buscarCategoriaPorNombre(String nombreCategoria) {
        return categoriaRepository.findByNombreCategoriaContaining(nombreCategoria);
    }

    public Categoria guardarOActualizarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public boolean existeCategoriaPorNombre(String nombreCategoria) {
        return categoriaRepository.existsByNombreCategoria(nombreCategoria);
    }
   
    public void eliminarCategoriaPorId(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("La categoría con ID " + id + " no existe.");
        }
    }
    
    public long contarCategorias() {
        return categoriaRepository.count();
    }
}

