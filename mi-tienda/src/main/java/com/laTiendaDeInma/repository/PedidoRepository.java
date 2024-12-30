package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByUsuario_IdUsuario(Long idUsuario);
}
