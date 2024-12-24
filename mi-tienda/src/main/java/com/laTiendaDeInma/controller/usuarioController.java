package com.laTiendaDeInma.controller;

import com.laTiendaDeInma.model.Usuario;
import com.laTiendaDeInma.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession; 


@Controller
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestParam("nombre") String nombre, 
                        @RequestParam("contraseña") String contraseña, 
                        HttpSession session, 
                        Model model) {

        boolean usuarioValido = usuarioService.validarUsuario(nombre, contraseña);
        Usuario usuario = usuarioService.buscarPorNombreYContraseña(nombre, contraseña);
        if (usuarioValido) {
            session.setAttribute("usuario", usuario);
            return "base";  // Redirige a la página de inicio si el usuario es válido
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            return "login";  // Vuelve al formulario de login con un mensaje de error
        }
    }
   
  @PostMapping("/intentoregistro")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model)
 {      System.out.println("Datos recibidos: " + usuario);
        // Validaciones en el backend
        if (result.hasErrors()) {
            return "registro"; // Vuelve a la vista de registro si hay errores
        }
        
        // Verificar si el correo ya está registrado (puedes agregar esta validación en el servicio)
        if (usuarioService.existeUsuarioPoremail(usuario.getEmail())) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            return "registro"; // Vuelve a la vista de registro con el mensaje de error
        }

        // Guardar el nuevo usuario en la base de datos
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        System.out.println("Usuario guardado: " + usuarioGuardado);
        return "redirect:/login";  // Redirigir al login después del registro
    }

      @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}
