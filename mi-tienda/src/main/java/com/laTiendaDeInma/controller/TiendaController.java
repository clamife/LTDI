package com.laTiendaDeInma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.laTiendaDeInma.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.usuarioService;
import java.util.List;
@Controller
public class TiendaController {
    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private CategoriaService categoriaService;

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

    @GetMapping("/gsdgr")//TODO tengo pte gestionar mi vida
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

    @GetMapping("/nuevaCategoria")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "nuevaCategoria";
    }

    

}
