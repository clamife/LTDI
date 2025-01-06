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

    public Pedido confirmarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        pedido.setEstado("confirmado");
        
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
    
    public Pedido guardarPedido(Pedido pedido) {
         return pedidoRepository.save(pedido);
    }
    public List<Pedido> obtenerPedidosPorIdUsuario(Long idUsuario) {
        return pedidoRepository.findByUsuario_IdUsuario(idUsuario);
    }
}
