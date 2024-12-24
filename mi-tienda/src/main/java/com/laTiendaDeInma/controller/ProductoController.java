package com.laTiendaDeInma.controller;

import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.Foto;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.FotoService;
import com.laTiendaDeInma.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FotoService fotoService;

     @Autowired
    private CategoriaService categoriaService;

    // Mostrar todos los productos
    @GetMapping("/gestionProductos")
    public String getAllProductos(Model model) {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        for (Producto producto : productos) {
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto()); // Obtener fotos por idProducto
            producto.setFotos(fotos); // Asignar las fotos al producto
        }
        model.addAttribute("productos", productos); // Pasar productos con fotos asociadas
        return "gestionProductos"; // Nombre de la vista
}
    @GetMapping("/productos/{idCate}")
    public String getAllProductosvista(@PathVariable("idCate") Long idCate, Model model) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(idCate)
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada para el id: " + idCate));
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        List<Producto> productos = productoService.obtenerProductosPorCategoria(idCate);
        for (Producto producto : productos) {
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto());
            producto.setFotos(fotos); 
        model.addAttribute("categoria", categoria);
        model.addAttribute("productos", productos);
        }return "productos"; 
    }



    // Mostrar los productos activos
    @GetMapping("/activos")
    public String obtenerProductosActivos(Model model) {
        List<Producto> productosActivos = productoService.obtenerProductosActivos();
        model.addAttribute("productos", productosActivos);
        return "productos/lista";  // Vista que muestra los productos activos
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoProducto(Model model) {
        // Obtener todas las categorías para el desplegable
        List<Categoria> categorias = categoriaService.obtenerTodas();
        List<String> urls = new ArrayList<>();
        model.addAttribute("categorias", categorias);
        model.addAttribute("urls", urls); 
        model.addAttribute("producto", new Producto());
        return "nuevoProducto";  // Vista para crear un nuevo producto
    }

    // Guardar nuevo producto
    @PostMapping("/nuevo")
    @Transactional
    public String guardarProducto(@ModelAttribute Producto producto, 
            @RequestParam("categoria") Long idCategoria, // ID de la categoría seleccionada
            @RequestParam List<String> urls, // Lista de URLs de fotos
            Model model) {
        // Validar que se hayan proporcionado URLs para las fotos
        if (urls == null || urls.isEmpty()) {
            model.addAttribute("error", "Debes proporcionar al menos una URL para las fotos.");
            return "nuevoProducto"; // Volver al formulario con un mensaje de error
        }

        // Establecer la categoría seleccionada en el producto
        Categoria categoria = categoriaService.obtenerCategoriaPorId(idCategoria)
          .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria); // Asignar la categoría al producto

        // Guardar el producto en la base de datos
        productoService.guardarProducto(producto);

        // Guardar las fotos asociadas al producto
        for (String url : urls) {
            String cleanedUrl = url.trim().replace("[", "").replace("]", "").replace("\"", "");
            Foto foto = new Foto();
            foto.setProducto(producto); // Relación con el producto
            foto.setUrlFoto(cleanedUrl);      // Establecer la URL de la foto
            fotoService.guardarFoto(foto); // Guardar la foto en la base de datos
        }

        return "redirect:/gestionProductos"; // Redirigir a la lista de productos
    }

    // Ver el detalle de un producto
    @GetMapping("/producto/{id}")
    public String verProducto(@PathVariable Long id, Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(id);  
            model.addAttribute("fotos", fotos);
            return "detalleProducto"; 
        }
        return "redirect:/productos";  
    }

    // Eliminar un producto
    @DeleteMapping("/eliminar/{id}")
public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
    boolean eliminado = productoService.eliminarProducto(id);
    if (eliminado) {
        fotoService.eliminarFotosPorProducto(id);
        return ResponseEntity.ok("Producto y fotos eliminados correctamente");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el producto");
    }
}

    // Actualizar un producto
    @GetMapping("/editarProducto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        List<Categoria> categorias = categoriaService.obtenerTodas();
        List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto());
        producto.setFotos(fotos);
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
    
    return "editarProducto";
}

    @PostMapping("/editarProducto/{id}")
    public String actualizarProducto(@PathVariable Long id, 
                                    @ModelAttribute Producto producto, 
                                    @RequestParam("categoria") Long idCategoria,
                                    @RequestParam("photoUrlsJson") List<String> photoUrls) {
        if (productoService.existeProducto(id)) {
            producto.setIdProducto(id); 
            Categoria categoria = categoriaService.obtenerCategoriaPorId(idCategoria)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria); 
            productoService.actualizarProducto(producto, photoUrls);
            return "redirect:/gestionProductos";
        }
        return "error";  //no esta creado ya vere 
    }

    // Buscar productos por nombre
    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam("nombre") String nombre, Model model) {
        List<Producto> productos = productoService.buscarProductosPorNombre(nombre);
        model.addAttribute("productos", productos);
        return "productos/lista";  // Vista que muestra los productos encontrados
    }
}
