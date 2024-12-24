package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.Foto;
import com.laTiendaDeInma.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    // Método para guardar una foto en la base de datos
    @Transactional

    public List<Foto> obtenerTodas() {
        return fotoRepository.findAll();
    }

    public Foto guardarFoto(Foto foto) {
        return fotoRepository.save(foto);
    }

    // Método para obtener todas las fotos de un producto específico
    public List<Foto> obtenerFotosPorProducto(Long idProducto) {
        return fotoRepository.findByProductoIdProducto(idProducto);
    }

    // Método para eliminar una foto por ID
    public boolean eliminarFoto(Long idFoto) {
        try {
            fotoRepository.deleteById(idFoto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //Método para eliminar todas las fotos po ID Producto
    public boolean eliminarFotosPorProducto(Long idProducto) {
        try {
            fotoRepository.deleteByProducto_IdProducto(idProducto);
            return true; 
        } catch (Exception e) {
            return false; 
        }
    }
    
}
