package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
      
      List<Opinion> findByProducto_IdProducto(Long idProducto);
      List<Opinion> findByUsuario_IdUsuario(Long idUsuario);
      List<Opinion> findByUsuario_IdUsuarioAndProducto_IdProducto(Long idUsuario, Long idProducto);

}