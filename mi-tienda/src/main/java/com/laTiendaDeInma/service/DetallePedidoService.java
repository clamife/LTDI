package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.DetallePedido;
import com.laTiendaDeInma.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    // Guardar un detalle de pedido
    public DetallePedido guardar(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    // Obtener todos los detalles de pedidos
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoRepository.findAll();
    }
    
    public List<DetallePedido> obtenerTodosPorIdpedido(Long idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }
}
