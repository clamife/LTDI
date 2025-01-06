package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.DetallePedido;
import com.laTiendaDeInma.model.Opinion;
import com.laTiendaDeInma.model.Pedido;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.model.Usuario;
import com.laTiendaDeInma.repository.usuarioRepository;
import com.laTiendaDeInma.repository.PedidoRepository;
import com.laTiendaDeInma.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;


@Service
public class usuarioService {

    @Autowired
    private usuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    

    // Método para guardar un usuario
    public Usuario guardarUsuario(Usuario usuario) {
        String contrasenaHasheada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaHasheada);
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
    public Optional<Usuario> encontrarUsuarioPornombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    // Método para modificar un usuario
    public Usuario modificarUsuario(Long id, Usuario usuarioNuevo) {
        if (usuarioRepository.existsById(id)) {
            usuarioNuevo.setIdUsuario(id); 
            return usuarioRepository.save(usuarioNuevo);
        } else {
            return null;
        }
    }
    public Usuario buscarPorNombreYContraseña(String nombre, String contraseña) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombre(nombre);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contraseña, usuario.getContrasena())) {
                return usuario;
            }
        }
        return null;  
    }

    public boolean validarUsuario(String nombre, String contraseña) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombre(nombre);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return passwordEncoder.matches(contraseña, usuario.getContrasena());
        }
        return false; 
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
    public List<Producto> obtenerProductosComprados(Long idUsuario) {
        return pedidoRepository.findByUsuario_IdUsuario(idUsuario).stream()
            .sorted(Comparator.comparing(Pedido::getFechaPedido).reversed()) 
            .flatMap(pedido -> detallePedidoRepository.findByPedido_IdPedido(pedido.getIdPedido()).stream())
            .map(DetallePedido::getProducto)
            .distinct()
            .collect(Collectors.toList());
    }
    
}

