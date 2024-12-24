package com.laTiendaDeInma.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fotos_producto")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoto;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false) // Relación con Producto
    private Producto producto;  // Relación con la tabla Producto

    private String urlFoto;

    // Getters y setters
    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
