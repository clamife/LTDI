package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedido_IdPedido(Long idPedido);
}
