package com.laTiendaDeInma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.laTiendaDeInma.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.DetallePedidoService;
import com.laTiendaDeInma.service.PedidoService;
import com.laTiendaDeInma.service.ProductoService;
import com.laTiendaDeInma.service.usuarioService;

import jakarta.validation.Valid;

import com.laTiendaDeInma.service.FotoService;
import com.laTiendaDeInma.service.OpinionService;

import java.util.List;

@Controller
public class OpinionController {
    
    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private FotoService fotoService;
    @Autowired
    private OpinionService opinionService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/miZonaproductosComprados/{idUsuario}")
    public String productosComprados(@PathVariable long idUsuario, Model model){
        List<Producto> productos = usuarioService.obtenerProductosComprados(idUsuario);
        for (Producto producto : productos) {
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto()); 
            producto.setFotos(fotos);
        }
        model.addAttribute("productos", productos);
        return "miZonaproductosComprados";
    }
    @GetMapping("/miZonaComentarios/{idProducto}/{idUsuario}")
    public String comentarioNuevo(@PathVariable long idProducto,@PathVariable long idUsuario, Model model){
        Producto producto = productoService.obtenerProductoPorId(idProducto)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado para el id: " + idProducto));
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto()); 
            producto.setFotos(fotos);
            List<Opinion> opiniones = opinionService.obtenerOpinionesPorUsuarioYProducto(idUsuario, idProducto);
            Collections.reverse(opiniones); 
            producto.setOpiniones(opiniones);
        model.addAttribute("producto", producto);
        return "/miZonaComentarios";
    }
    @GetMapping("/miZonaComentarioNuevo/{idProducto}")
    public String comentarioNuevo(@PathVariable long idProducto, Model model){
        Producto producto = productoService.obtenerProductoPorId(idProducto)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado para el id: " + idProducto));
        model.addAttribute("producto", producto);
        model.addAttribute("opinion", new Opinion()) ;
        return"/miZonaComentarioNuevo";
    }
    @PostMapping("/miZonaComentarios/{idProducto}/{idUsuario}")
    public String intentoComentarioNuevo(@Valid @ModelAttribute Opinion opinion,@PathVariable long idProducto,@PathVariable long idUsuario, Model model){
        productoService.agregarOpinion(idProducto, opinion.getComentario(), opinion.getCalificacion(),idUsuario);
        Producto producto = productoService.obtenerProductoPorId(idProducto)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado para el id: " + idProducto));
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto()); 
            producto.setFotos(fotos);
            List<Opinion> opiniones = opinionService.obtenerOpinionesPorUsuarioYProducto(idUsuario, idProducto);
            producto.setOpiniones(opiniones);
        model.addAttribute("producto", producto);
        return"redirect:/miZonaComentarios/"+ idProducto + "/" + idUsuario;
    }
    @DeleteMapping("/miZonaEliminarComentario/{idComentario}")
    public ResponseEntity<String> eliminarComentario(@PathVariable Long idComentario) {
        try {
            opinionService.eliminarOpinion(idComentario);
            return ResponseEntity.ok("Comentario eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Hubo un error al eliminar el comentario.");
        }
    }
}
