package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.Pedido;
import com.laTiendaDeInma.model.DetallePedido;
import com.laTiendaDeInma.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoService detallePedidoService;

    // Crear un nuevo pedido con los detalles
    public Pedido crearPedido(Pedido pedido, List<DetallePedido> detalles) {
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstado("pendiente");
        
        double total = 0;
        for (DetallePedido detalle : detalles) {
            total += detalle.getCantidad() * detalle.getPrecioUnitario();
        }
        pedido.setTotal(total);
        pedido.setDetalles(detalles);
        
        return pedidoRepository.save(pedido);
    }

    // Confirmar el pedido
    public Pedido confirmarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        pedido.setEstado("confirmado");
        
        return pedidoRepository.save(pedido);
    }

    // Obtener todos los pedidos
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    // Obtener un pedido por ID
    public Pedido obtenerPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
}
