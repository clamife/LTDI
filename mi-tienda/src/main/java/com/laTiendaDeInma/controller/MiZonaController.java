package com.laTiendaDeInma.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.Pedido;
import com.laTiendaDeInma.model.Recurso;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.DetallePedidoService;
import com.laTiendaDeInma.service.PedidoService;
import com.laTiendaDeInma.service.RecursoService;
import com.laTiendaDeInma.service.usuarioService;

@Controller
public class MiZonaController {
    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private RecursoService recursoService;

    @GetMapping("/miZona/{idUsuario}")
    public String miZona(@PathVariable Long idUsuario ,Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        List<Producto> productos = usuarioService.obtenerProductosComprados(idUsuario);
        for (Producto producto : productos) {
            List<Recurso> recursos = recursoService.obtenerRecursosPorProducto(producto.getIdProducto());
            producto.setRecursos(recursos);
        }
        model.addAttribute("productos", productos);
        return "miZona";
    }
    
     @GetMapping("/miZonaPedidos/{idUsuario}")
    public String miZonaPedidos(@PathVariable Long idUsuario, Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        List<Pedido> pedido = pedidoService.obtenerPedidosPorIdUsuario(idUsuario);
        Collections.reverse(pedido);
        model.addAttribute("pedido", pedido);
        return "miZonaPedidos";
    }
    @GetMapping("/miZonaDetallePedido/{idPedido}")
    public String miZonaDetallesPedidos(@PathVariable Long idPedido, Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        Pedido pedido = pedidoService.obtenerPorId(idPedido);
        model.addAttribute("pedido", pedido);
        return "miZonaDetallePedido";
    }

}
