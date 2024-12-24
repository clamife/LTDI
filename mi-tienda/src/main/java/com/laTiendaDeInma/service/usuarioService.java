package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.Usuario;
import com.laTiendaDeInma.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    

    // Método para guardar un usuario
    public Usuario guardarUsuario(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

    // Método para comprobar si un usuario existe por ID
    public boolean existeUsuarioPorId(Long id) {
        return usuarioRepository.existsById(id);
    }

    // Método para comprobar si un usuario existe por nombre de usuario
    public boolean existeUsuarioPorNombre(String nombre) {
        return usuarioRepository.existsByNombre(nombre);
    }
   
    // Método para comprobar si un usuario existe por nombre de usuario
    public boolean existeUsuarioPoremail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Método para encontrar un usuario por ID
    public Optional<Usuario> encontrarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para modificar un usuario
    public Usuario modificarUsuario(Long id, Usuario usuarioNuevo) {
        if (usuarioRepository.existsById(id)) {
            usuarioNuevo.setIdUsuario(id); // Asegura que el usuario nuevo tiene el mismo ID
            return usuarioRepository.save(usuarioNuevo);
        } else {
            return null; // Retorna null si no se encuentra el usuario
        }
    }
    public Usuario buscarPorNombreYContraseña(String nombre, String contraseña) {
        return usuarioRepository.findByNombreAndContraseña(nombre, contraseña)
        .orElse(null);
    }
    public boolean validarUsuario(String nombre, String contraseña) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreAndContraseña(nombre, contraseña);
        return usuarioOpt.isPresent();
    }

    // Método para eliminar un usuario por ID
    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
   

public long contarUsuarios() {
    return usuarioRepository.count();  // Devuelve el número de usuarios en la base de datos
}
}
