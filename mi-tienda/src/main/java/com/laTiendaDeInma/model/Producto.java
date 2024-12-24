package com.laTiendaDeInma.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "productos") 
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "stock")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con la tabla categorías
    @JoinColumn(name = "id_categoria")  // Nombre de la columna que actúa como clave foránea
    private Categoria categoria;  // Relacionado con la entidad Categoria

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<Foto> fotos; // Relación con la tabla Fotos

    @Column(name = "activo")
    private Boolean activo;

    // Getters y setters

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
    public List<Foto> getFotos() {
        return fotos;
    }

}
