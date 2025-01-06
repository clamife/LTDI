package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Recurso;
import com.laTiendaDeInma.model.TipoRecurso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByProducto_IdProducto(Long idProducto);
    List<Recurso> findByTipoRecurso(TipoRecurso tipoRecurso); 
    boolean existsByNombreRecurso(String nombreRecurso);
}
