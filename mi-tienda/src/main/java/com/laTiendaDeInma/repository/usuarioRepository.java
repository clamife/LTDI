package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 
import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Long> {
    // Encontrar todos los usuarios
    List<Usuario> findAll();
    
    // Guardar un usuario (Este método ya está disponible con 'save' de JpaRepository, pero lo incluimos por claridad)
    @Override
    Usuario save(Usuario usuario);

    // Comprobar si un usuario existe por su id
    boolean existsById(Long idUsuario);

    // Comprobar si un usuario existe por su correo electrónico
    boolean existsByEmail(String email);

     // Comprobar si un usuario existe por su nombre
     boolean existsByNombre(String nombre);

    // Modificar un usuario (Este es un ejemplo de como se podría hacer, pero JpaRepository ya tiene 'save' que cubre esto)
    // Si deseas un método que haga más cosas como validar o manejar actualizaciones de alguna manera específica, puedes agregarlo.
    @Override
    <S extends Usuario> S saveAndFlush(S usuario);

    // Eliminar un usuario por id
    void deleteById(Long id);

    // Buscar un usuario por correo electrónico 
    Optional<Usuario> findByEmail(String email);

    // Buscar un usuario por nombre de usuario
    Optional<Usuario> findByNombre(String nombre);
    // Busca un usuario por nombre y contraseña
    Optional<Usuario> findByNombreAndContraseña(String nombre, String contraseña);
    
}



