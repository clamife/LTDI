package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 
import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Long> {
   
    List<Usuario> findAll();
    
    boolean existsById(Long idUsuario);
    boolean existsByEmail(String email);
    boolean existsByNombre(String nombre);

    @Override
    <S extends Usuario> S saveAndFlush(S usuario);

    void deleteById(Long id);

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByNombreAndContraseña(String nombre, String contraseña);
    
}



