package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Verificar si un producto existe por su ID
    boolean existsById(Long idProducto);

    Optional<Producto> findById(Long idProducto);

    // Obtener productos activos
    List<Producto> findByActivoTrue();

    // Buscar productos por nombre
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);

    // Buscar productos por descripción
    List<Producto> findByDescripcionContainingIgnoreCase(String descripcion);

    // Consultar productos por categoría (relación ya definida)
    List<Producto> findByCategoria(Categoria categoria);

    // Consultar productos por categoría usando su id
    List<Producto> findByCategoriaIdCategoria(Long categoriaId);
     // Busca productos que contengan un nombre específico (ignorando mayúsculas y minúsculas)
     List<Producto> findBynombreProductoContainingIgnoreCase(String nombreProducto);

     // Actualiza un producto (usando save para actualizar si existe)
     default Producto actualizarProducto(Producto producto) {
         if (producto.getIdProducto() != null) {
             return save(producto);
         }
         throw new IllegalArgumentException("El producto no tiene un ID asignado.");
     }
}
