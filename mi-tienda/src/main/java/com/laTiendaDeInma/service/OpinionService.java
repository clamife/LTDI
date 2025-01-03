package com.laTiendaDeInma.service;


import com.laTiendaDeInma.model.Opinion;
import com.laTiendaDeInma.repository.DetallePedidoRepository;
import com.laTiendaDeInma.repository.OpinionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OpinionService {
    @Autowired
    private OpinionRepository opinionRepository;

    // Método para guardar una nueva opinión
    public Opinion guardarOpinion(Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    // Método para obtener todas las opiniones
    public List<Opinion> obtenerTodasLasOpiniones() {
        return opinionRepository.findAll();
    }

    // Método para obtener una opinión por su ID
    public Optional<Opinion> obtenerOpinionPorId(Long idOpinion) {
        return opinionRepository.findById(idOpinion);
    }

    // Método para obtener todas las opiniones de un producto específico
    public List<Opinion> obtenerOpinionesPorProducto(Long idProducto) {
        return opinionRepository.findByProducto_IdProducto(idProducto);
    }

    // Método para obtener todas las opiniones de un usuario específico
    public List<Opinion> obtenerOpinionesPorUsuario(Long idUsuario) {
        return opinionRepository.findByUsuario_IdUsuario(idUsuario);
    }
    // Método para obtener las opiniones de un usuario sobre un producto específico
    public List<Opinion> obtenerOpinionesPorUsuarioYProducto(Long idUsuario, Long idProducto) {
        return opinionRepository.findByUsuario_IdUsuarioAndProducto_IdProducto(idUsuario, idProducto);
    }

    // Método para actualizar una opinión existente
    public Opinion actualizarOpinion(Long idOpinion, Opinion nuevaOpinion) {
        if (opinionRepository.existsById(idOpinion)) {
            nuevaOpinion.setIdOpinion(idOpinion);
            return opinionRepository.save(nuevaOpinion);
        }
        return null; // Retorna null si no se encuentra la opinión con el ID proporcionado
    }

    // Método para eliminar una opinión por su ID
    public boolean eliminarOpinion(Long idOpinion) {
        if (opinionRepository.existsById(idOpinion)) {
            opinionRepository.deleteById(idOpinion);
            return true;
        }
        return false; // Retorna false si no se encuentra la opinión con el ID proporcionado
    }

    // Método para obtener la calificación promedio de un producto
    public Double obtenerCalificacionPromedio(Long idProducto) {
        List<Opinion> opiniones = opinionRepository.findByProducto_IdProducto(idProducto);
        if (opiniones.isEmpty()) {
            return null; // Si no hay opiniones, retorna null
        }
        return opiniones.stream()
                .mapToInt(opinion -> opinion.getCalificacion())
                .average() // Calcula el promedio
                .orElse(0.0); // Si la lista está vacía, retorna 0.0
    }
}
