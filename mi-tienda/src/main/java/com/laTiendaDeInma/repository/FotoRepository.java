package com.laTiendaDeInma.repository;


import com.laTiendaDeInma.model.Foto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    

    List<Foto> findByProductoIdProducto(Long idProducto);

    void deleteByProducto_IdProducto(Long idProducto); 
}
