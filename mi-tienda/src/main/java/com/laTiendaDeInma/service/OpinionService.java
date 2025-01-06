package com.laTiendaDeInma.service;


import com.laTiendaDeInma.model.Opinion;
import com.laTiendaDeInma.repository.OpinionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OpinionService {
    @Autowired
    private OpinionRepository opinionRepository;

    public Opinion guardarOpinion(Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    public List<Opinion> obtenerTodasLasOpiniones() {
        return opinionRepository.findAll();
    }

    public Optional<Opinion> obtenerOpinionPorId(Long idOpinion) {
        return opinionRepository.findById(idOpinion);
    }

    public List<Opinion> obtenerOpinionesPorProducto(Long idProducto) {
        return opinionRepository.findByProducto_IdProducto(idProducto);
    }

    public List<Opinion> obtenerOpinionesPorUsuario(Long idUsuario) {
        return opinionRepository.findByUsuario_IdUsuario(idUsuario);
    }
    public List<Opinion> obtenerOpinionesPorUsuarioYProducto(Long idUsuario, Long idProducto) {
        return opinionRepository.findByUsuario_IdUsuarioAndProducto_IdProducto(idUsuario, idProducto);
    }

    public Opinion actualizarOpinion(Long idOpinion, Opinion nuevaOpinion) {
        if (opinionRepository.existsById(idOpinion)) {
            nuevaOpinion.setIdOpinion(idOpinion);
            return opinionRepository.save(nuevaOpinion);
        }
        return null; 
    }

    public boolean eliminarOpinion(Long idOpinion) {
        if (opinionRepository.existsById(idOpinion)) {
            opinionRepository.deleteById(idOpinion);
            return true;
        }
        return false;
    }

}
