package com.laTiendaDeInma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/nuevaCategoria")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "nuevaCategoria";
    }
    @PostMapping("/registrocategoria")
    public String registrarCategoria(
            @Valid @ModelAttribute("categoria") Categoria categoria,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor, corrige los errores del formulario.");
            return "registro"; 
        }

        if (categoriaService.existeCategoriaPorNombre(categoria.getNombreCategoria())) {
            model.addAttribute("error", "La categoría ya está registrada.");
            return "registro"; 
        }

        categoriaService.guardarOActualizarCategoria(categoria);
        return "redirect:/gestionCategorias";
    }
    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminarCategoriaPorId(id); 
            return ResponseEntity.ok("Categoría eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar la categoría: " + e.getMessage());
        }
    }

}
