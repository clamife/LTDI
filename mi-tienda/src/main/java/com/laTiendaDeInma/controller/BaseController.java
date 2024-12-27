package com.laTiendaDeInma.controller;

//TODO modificar la lógica de las partes repetidas con @ControllerAdvice con @ModelAttribute para las categorías y el usuario. No funciona no se porue 
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.service.CategoriaService;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;


@Controller
public class BaseController {

    @Autowired
    private CategoriaService categoriaService;

    @ModelAttribute("categorias")
    public List<Categoria> agregarCategoriasAlModelo() {

        return categoriaService.obtenerTodas();

    }
}
