package com.laTiendaDeInma.controller;

import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.Foto;
import com.laTiendaDeInma.model.Opinion;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.FotoService;
import com.laTiendaDeInma.service.OpinionService;
import com.laTiendaDeInma.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private OpinionService opinionService;

    @GetMapping("/gestionProductos")
    public String getAllProductos(Model model) {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        for (Producto producto : productos) {
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(producto.getIdProducto()); 
            producto.setFotos(fotos);
        }
        model.addAttribute("productos", productos);
        return "gestionProductos"; 
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



    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoProducto(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        List<String> urls = new ArrayList<>();
        model.addAttribute("categorias", categorias);
        model.addAttribute("urls", urls); 
        model.addAttribute("producto", new Producto());
        return "nuevoProducto";  
    }

    @PostMapping("/nuevo")
    @Transactional
    public String guardarProducto(@ModelAttribute Producto producto, 
            @RequestParam("categoria") Long idCategoria, 
            @RequestParam List<String> urls,
            Model model) {
        if (urls == null || urls.isEmpty()) {
            model.addAttribute("error", "Debes proporcionar al menos una URL para las fotos.");
            return "nuevoProducto"; 
        }

        Categoria categoria = categoriaService.obtenerCategoriaPorId(idCategoria)
          .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setCategoria(categoria);
        productoService.guardarProducto(producto);

        for (String url : urls) {
            String cleanedUrl = url.trim().replace("[", "").replace("]", "").replace("\"", "");
            Foto foto = new Foto();
            foto.setProducto(producto); 
            foto.setUrlFoto(cleanedUrl);   
            fotoService.guardarFoto(foto); 
        }

        return "redirect:/gestionProductos"; 
    }

    @GetMapping("/producto/{id}")
    public String verProducto(@PathVariable Long id, Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            List<Foto> fotos = fotoService.obtenerFotosPorProducto(id);  
            model.addAttribute("fotos", fotos);
            List<Opinion> opiniones = opinionService.obtenerOpinionesPorProducto(id);
            Collections.reverse(opiniones); 
            model.addAttribute("opiniones", opiniones);
            return "detalleProducto"; 
        }
        return "redirect:/productos";  
    }

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

    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam("nombre") String nombre, Model model) {
        List<Producto> productos = productoService.buscarProductosPorNombre(nombre);
        model.addAttribute("productos", productos);
        return "productos/lista"; 
    }
}
