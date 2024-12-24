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


    // Método para obtener todas las categorías
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    // Método para obtener una categoría por su ID
    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    // Método para buscar categorías por nombre
    public List<Categoria> buscarCategoriaPorNombre(String nombreCategoria) {
        return categoriaRepository.findByNombreCategoriaContaining(nombreCategoria);
    }

    // Método para guardar o actualizar una categoría
    public Categoria guardarOActualizarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public boolean existeCategoriaPorNombre(String nombreCategoria) {
        return categoriaRepository.existsByNombreCategoria(nombreCategoria);
    }
   

    // Método para eliminar una categoría por su ID
    public void eliminarCategoriaPorId(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("La categoría con ID " + id + " no existe.");
        }
    }
    

    // Método para contar el número de categorías en la base de datos
    public long contarCategorias() {
        return categoriaRepository.count();
    }
}

