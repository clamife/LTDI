package com.laTiendaDeInma.controller;

import com.laTiendaDeInma.model.Pedido;
import com.laTiendaDeInma.model.Categoria;
import com.laTiendaDeInma.model.DetallePedido;
import java.time.LocalDate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.laTiendaDeInma.service.PedidoService;
import com.laTiendaDeInma.service.CategoriaService;
import com.laTiendaDeInma.service.DetallePedidoService;
import com.laTiendaDeInma.model.Producto;
import com.laTiendaDeInma.model.Usuario;
import com.laTiendaDeInma.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private ProductoService productoService; 

    // Método para añadir un producto al carrito (pedido)
    @GetMapping("/carritoAdd/{idProducto}")
    public String addToCarrito(@PathVariable Long idProducto, HttpSession session, Model model) {
        // Obtener el pedido de la sesión o crear uno nuevo si no existe
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if (pedido == null) {
            pedido = new Pedido();
            session.setAttribute("pedido", pedido);  // Guardar el pedido vacío en sesión
        }

        if (pedido.getDetalles() == null) {
        pedido.setDetalles(new ArrayList<>());
         }

        // Obtener el producto
        Optional<Producto> productoOpt = productoService.obtenerProductoPorId(idProducto);
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            
            DetallePedido detalleExistente = pedido.getDetalles().stream()
                .filter(d -> d.getProducto().getIdProducto().equals(idProducto))
                .findFirst().orElse(null);

            if (detalleExistente != null) {
                // Si ya está, aumentamos la cantidad
                detalleExistente.setCantidad(detalleExistente.getCantidad() + 1);
            } else {
                // Si no está, lo añadimos al detalle
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setProducto(producto);
                detallePedido.setCantidad(1);
                detallePedido.setPrecioUnitario(producto.getPrecio()); // Asumiendo que tienes un campo precio
                pedido.getDetalles().add(detallePedido);
            }

            double total = pedido.getDetalles().stream()
            .mapToDouble(detalle -> detalle.getPrecioUnitario() * detalle.getCantidad())
            .sum();
            pedido.setTotal(total); // Actualizamos el total del pedido
            session.setAttribute("pedido", pedido);
            }

        return "redirect:/carrito"; 
    }
    @DeleteMapping("/eliminarDetalle/{nombreProducto}")
    public ResponseEntity<String> eliminarDetalle(@PathVariable String nombreProducto, HttpSession session, Model model) {
        // Obtener el pedido de la sesión
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if (pedido != null && pedido.getDetalles() != null) {
            // Buscar el detalle que se quiere eliminar por nombre de producto
            DetallePedido detalleAEliminar = pedido.getDetalles().stream()
                .filter(d -> d.getProducto().getNombreProducto().equals(nombreProducto))  // Filtramos por nombre del producto
                .findFirst()
                .orElse(null);
    
            if (detalleAEliminar != null) {
                // Si la cantidad es mayor a 1, simplemente restamos la cantidad
                if (detalleAEliminar.getCantidad() > 1) {
                    detalleAEliminar.setCantidad(detalleAEliminar.getCantidad() - 1);
                } else {
                    // Si la cantidad es 1, eliminamos el detalle
                    pedido.getDetalles().remove(detalleAEliminar);
                }
    
                // Recalcular el total del pedido
                double total = pedido.getDetalles().stream()
                    .mapToDouble(detalle -> detalle.getPrecioUnitario() * detalle.getCantidad())
                    .sum();
                pedido.setTotal(total); // Actualizamos el total del pedido
                session.setAttribute("pedido", pedido);

                return ResponseEntity.ok("Producto eliminado del carrito");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado en el carrito");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay pedido en la sesión");
        }
    
    }
    


    // Mostrar el carrito
    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        Pedido pedido = (Pedido) session.getAttribute("pedido");

        // Si no existe un pedido en sesión, se muestra el carrito vacío
        if (pedido == null || pedido.getDetalles().isEmpty()) {
            model.addAttribute("mensaje", "Tu carrito está vacío.");
            
        List<Categoria> categoria = categoriaService.obtenerTodas();
        model.addAttribute("categoria", categoria);
        
            return "carrito"; // Vista de carrito vacío
        }

        // Si el carrito tiene productos, se muestra normalmente
        model.addAttribute("pedido", pedido);
        return "carrito"; // Vista de carrito con productos
    }

    // Finalizar compra (requiere que el usuario esté logueado)
    @GetMapping("/finalizarPedido")
    @Transactional
    public String finalizarCompra(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (pedido == null || pedido.getDetalles().isEmpty()) {
            return "redirect:/carrito";
        }
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Debes iniciar sesión para realizar un pedido.");
            return "redirect:/login";
        }
        model.addAttribute("pedido", pedido);
        return "/finalizarPedido"; 
    }

    @PostMapping("/confirmarCompra")
    @Transactional
    public String confirmarCompra(@ModelAttribute("pedido") Pedido pedidoFormulario, HttpSession session, Model model){
        Pedido pedidoSes = (Pedido) session.getAttribute("pedido");
        List<DetallePedido> detalles = pedidoSes.getDetalles();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        pedidoSes.setEstado("En preparación");
        pedidoSes.setFechaPedido(LocalDate.now()); 
        pedidoSes.setUsuario(usuario);
        pedidoSes.setDireccionEnvio(pedidoFormulario.getDireccionEnvio());
        pedidoSes.setTelefonoEnvio(pedidoFormulario.getTelefonoEnvio());
        pedidoSes.setCorreoEnvio(pedidoFormulario.getCorreoEnvio());
    
       Pedido pedidodoGu = pedidoService.guardarPedido(pedidoSes);
       System.out.println("Guardando pedido con ID: " + pedidodoGu.getIdPedido());
       for (DetallePedido detalle : detalles) {
        detalle.setPedido(pedidodoGu); 
        detallePedidoService.guardar(detalle); 
        }
        
        session.removeAttribute("pedido");
    
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        return "base"; 
    }
    @GetMapping("/gestionPedidos")
    public String gestionPedidos(Model model) {
        List<Pedido> pedido = pedidoService.obtenerTodos() ;
        model.addAttribute("pedido", pedido);
        return "gestionPedidos";
    }
    @GetMapping("/detallesPedido/{idPedido}")
    public String detallesPedidos(@PathVariable Long idPedido,Model model) {
        List<DetallePedido> detallePedido = detallePedidoService.obtenerTodosPorIdpedido(idPedido) ;
        model.addAttribute("detallePedido", detallePedido);
        return "detallesPedido";
    }
    @PostMapping("/actualizarEstadoPedido/{idPedido}")
    public String actualizarEstadoPedido(@PathVariable Long idPedido, @RequestParam String estado) {
        Pedido pedido = pedidoService.obtenerPorId(idPedido);
        if (pedido != null) {
            pedido.setEstado(estado);
            pedidoService.guardarPedido(pedido);
        }
        return "redirect:/gestionPedidos";
    }
    @GetMapping("/miZonaPedidos/{idUsuario}")
    public String miZonaPedidos(@PathVariable Long idUsuario, Model model) {
        List<Pedido> pedido = pedidoService.obtenerPedidosPorIdUsuario(idUsuario);
        model.addAttribute("pedido", pedido);
        return "miZonaPedidos";
    }
    @GetMapping("/miZonaDetallePedido/{idPedido}")
    public String miZonaDetallesPedidos(@PathVariable Long idPedido, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(idPedido);
        model.addAttribute("pedido", pedido);
        return "miZonaDetallePedido";
    }
}
