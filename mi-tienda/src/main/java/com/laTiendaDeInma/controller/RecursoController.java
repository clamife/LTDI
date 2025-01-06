package com.laTiendaDeInma.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.model.Pedido;
import com.laTiendaDeInma.model.Recurso;
import com.laTiendaDeInma.model.Usuario;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.DetallePedidoService;
import com.laTiendaDeInma.service.PedidoService;
import com.laTiendaDeInma.service.ProductoService;
import com.laTiendaDeInma.service.RecursoService;
import com.laTiendaDeInma.service.usuarioService;

import jakarta.validation.Valid;

@Controller
public class RecursoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private RecursoService recursoService;

    @GetMapping("/gestionRecursos/{idProducto}")
    private String gestionRecursos(@PathVariable Long idProducto, Model model){
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        List<Recurso> recursos = recursoService.obtenerRecursosPorProducto(idProducto);
        model.addAttribute("recursos", recursos);
        model.addAttribute("idProducto",  idProducto);
        return "gestionRecursos";
    }
    @DeleteMapping("/recurso/{idRecurso}")
    public ResponseEntity<String> eliminarRecurso(@PathVariable Long idRecurso) {
        try {
            recursoService.eliminarRecurso(idRecurso);
            return ResponseEntity.ok("Recurso eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar el recurso: " + e.getMessage());
        }
    }
    @GetMapping("/nuevoRecurso/{idProducto}")
    public String nuevoRecurso(@PathVariable Long idProducto, Model model){
        model.addAttribute("recurso", new Recurso());
        model.addAttribute("idProducto", idProducto);
        return "nuevoRecurso";
    }
    @PostMapping("/nuevoRecurso/{idProducto}")
    public String guardarRecurso(@PathVariable Long idProducto, Model model,
                                 @Valid @ModelAttribute("recurso") Recurso recurso,
                                 BindingResult result){
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor, corrige los errores del formulario.");
             return "redirect:/nuevoRecurso/" + idProducto;
        }
        Producto producto = productoService.obtenerProductoPorId(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado para el id: " + idProducto));
        recurso.setProducto(producto);
        recursoService.guardarRecurso(recurso);
        return "redirect:/gestionRecursos/"+ idProducto;
    }
}
