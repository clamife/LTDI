package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.Recurso;
import com.laTiendaDeInma.model.TipoRecurso;
import com.laTiendaDeInma.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    @Autowired
    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    public List<Recurso> obtenerTodosLosRecursos() {
        return recursoRepository.findAll();
    }
    public Optional<Recurso> obtenerRecursoPorId(Long id) {
        return recursoRepository.findById(id);
    }

    public Recurso guardarRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public void eliminarRecurso(Long id) {
        recursoRepository.deleteById(id);
    }
    public List<Recurso> obtenerRecursosPorProducto(Long idProducto) {
        return recursoRepository.findByProducto_IdProducto(idProducto);
    }

    public List<Recurso> obtenerRecursosPorTipo(TipoRecurso tipoRecurso) {
        return recursoRepository.findByTipoRecurso(tipoRecurso);
    }
    public boolean existeRecursoPorNombre(String nombreRecurso) {
        return recursoRepository.existsByNombreRecurso(nombreRecurso);
    }

}
