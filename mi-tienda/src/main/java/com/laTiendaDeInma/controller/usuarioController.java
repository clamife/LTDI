package com.laTiendaDeInma.controller;

import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.Usuario;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.usuarioService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession; 


@Controller
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/login")
    public String login(@RequestParam("nombre") String nombre, 
                    @RequestParam("contraseña") String contraseña, 
                    HttpSession session,
                    Model model) {
    Optional<Usuario> usuarioOpt = usuarioService.encontrarUsuarioPornombre(nombre);
    
    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        boolean usuarioValido = usuarioService.validarUsuario(nombre, contraseña);
        
        if (usuarioValido) {
            usuario.setContrasena("");
            session.setAttribute("usuario", usuario);
            List<Categoria> categorias = categoriaService.obtenerTodas();
            model.addAttribute("categorias", categorias);
            return "base";
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            List<Categoria> categorias = categoriaService.obtenerTodas();
            model.addAttribute("categorias", categorias);
            return "login"; 
        }
    } else {
        model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "login"; 
    }
}

   
  @PostMapping("/intentoregistro")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model)
 {      
        if (result.hasErrors()) {
            List<Categoria> categorias = categoriaService.obtenerTodas();
            model.addAttribute("categorias", categorias);
            return "registro"; 
        }
        
        if (usuarioService.existeUsuarioPoremail(usuario.getEmail())) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            List<Categoria> categorias = categoriaService.obtenerTodas();
            model.addAttribute("categorias", categorias);
            return "registro"; // Vuelve a la vista de registro con el mensaje de error
        }

        // Guardar el nuevo usuario en la base de datos
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "redirect:/login"; 
    }

      @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id, Model model ) {
        boolean eliminado = usuarioService.eliminarUsuario(id);
        if (eliminado) {
            List<Categoria> categorias = categoriaService.obtenerTodas();
            model.addAttribute("categorias", categorias);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            List<Categoria> categorias = categoriaService.obtenerTodas();
            model.addAttribute("categorias", categorias);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/base";  
    }
    @GetMapping("/usuario-sesion")
    @ResponseBody
    public Usuario obtenerUsuarioSesion(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Obtener el usuario desde la sesión
        return usuario != null ? usuario : null;  // Si no hay usuario, devolver null
    }
}
