package com.laTiendaDeInma.repository;

import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsById(Long idProducto);
    Optional<Producto> findById(Long idProducto);
    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);
    List<Producto> findByDescripcionContainingIgnoreCase(String descripcion);
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByCategoriaIdCategoria(Long categoriaId);
     List<Producto> findBynombreProductoContainingIgnoreCase(String nombreProducto);

     default Producto actualizarProducto(Producto producto) {
         if (producto.getIdProducto() != null) {
             return save(producto);
         }
         throw new IllegalArgumentException("El producto no tiene un ID asignado.");
     }
}
