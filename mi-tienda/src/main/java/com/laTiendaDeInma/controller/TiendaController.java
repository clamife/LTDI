package com.laTiendaDeInma.controller;
//TODO revisar todo el código para quitar redundancias y comentarios tontos 
//TODO controlar los mensjaes que se envian a los controladores con el RedirectAttributes
//TODO unificar estilos cuadros redondos y translucidos

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
        return "base"; 
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

}
