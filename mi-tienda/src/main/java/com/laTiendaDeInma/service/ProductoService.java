package com.laTiendaDeInma.service;

import com.laTiendaDeInma.model.Foto;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FotoService fotoService;

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Método para obtener un producto por su id
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Método para crear o actualizar un producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;  // Eliminación exitosa
        }
        return false;  // No se eliminó porque el producto no existe
    }
    
    // Obtener todos los productos por categoría
    public List<Producto> obtenerProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdCategoria(categoriaId);
    }

    // Obtener productos por nombre
    public List<Producto> obtenerProductosPorNombre(String nombreProducto) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombreProducto);
    }

    // Obtener productos por descripción
    public List<Producto> obtenerProductosPorDescripcion(String descripcion) {
        return productoRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }
    // Obtener todos los productos activos
    public List<Producto> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue(); // Asumimos que 'activo' es un campo booleano
    }
    //Método para saber si un producto existe por su id 
    public boolean existeProducto(Long id) {
        return productoRepository.existsById(id);
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }
    public void actualizarProducto(Producto producto, List<String> fotos) {
        productoRepository.save(producto);
        actualizarFotosDelProducto(producto, fotos);
    }
    private void actualizarFotosDelProducto(Producto producto, List<String> urls) {
        if(fotoService.eliminarFotosPorProducto(producto.getIdProducto())){
        for (String url : urls) {
            String cleanedUrl = url.trim().replace("[", "").replace("]", "").replace("}", "").replace("{", "").replace("\"", "");
            Foto fotonueva = new Foto();
            fotonueva.setProducto(producto); 
            fotonueva.setUrlFoto(cleanedUrl); 
            fotoService.guardarFoto(fotonueva);
        }
    }else{

    }
    }
}

