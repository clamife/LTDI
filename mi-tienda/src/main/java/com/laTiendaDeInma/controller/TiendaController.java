package com.laTiendaDeInma.controller;
//TODO MI ESPACIO: falta cambio de datos de usuario, reseñas y resources
//TODO Resources: todo 
//TODO revisar todo el código para quitar redundancias y comentarios tontos 
//TODO cambiar los alert por mensjaes buenos 
//TODO controlar los mensjaes que se envian a los controladores con el RedirectAttributes
//TODO Reseñas : todo 
//TODO si hay tiempo mirar lo de las subcategorias 
//TODO poner el buscador en el nav
//TODO pensar en como modificar el menu para que sea mejor 
//TODO unificar estilos cuadros redondos y translucidos
 //TODO cambiar que la lista de pedidos se vea el mas nuevo antes 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import com.laTiendaDeInma.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.DetallePedidoService;
import com.laTiendaDeInma.service.PedidoService;
import com.laTiendaDeInma.service.usuarioService;
import java.util.List;
@Controller
public class TiendaController {
    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping("/") 
    public String base(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "base"; // Vista de inicio
    }

    @GetMapping("/sobremi")
    public String sobremi(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "sobremi";
    }

    @GetMapping("/contacto")
    public String contaco(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "contacto";
    }

    @GetMapping("/preguntas")
    public String preguntas(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "preguntas";
    }
   
    @GetMapping("/login")
    public String login(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "login";
    }
    

    @GetMapping("/registro")
    public String registro(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @GetMapping("/baseAdmin")
    public String baseAdmin() {
        return "baseAdmin";
    }

    @GetMapping("/gestionUsuarios")
    public String gestionUsuarios(Model model) {
        List<Usuario> usuario = usuarioService.obtenerTodosLosUsuarios();
        model.addAttribute("usuario", usuario);
        return "gestionUsuarios";
    }

    @GetMapping("/gestionCategorias")
    public String listarCategorias(Model model) {
        List<Categoria> categoria = categoriaService.obtenerTodas();
        model.addAttribute("categoria", categoria);
        return "gestionCategorias";
    }
   

}
