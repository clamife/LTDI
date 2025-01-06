package com.laTiendaDeInma.model;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "recursos")
public class Recurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso")
    private Long idRecurso;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false) 
    private Producto producto;

    @NotBlank(message = "El nombre del recurso es obligatorio.")
    @Size(max = 100, message = "El nombre del recurso no puede superar los 100 caracteres.")
    @Column(name = "nombre_recurso")
    private String nombreRecurso;

    @NotNull(message = "El tipo de recurso es obligatorio.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recurso")
    private TipoRecurso tipoRecurso;

    @NotBlank(message = "La ruta del recurso es obligatoria.")
    @Column(name = "ruta_recurso")
    private String rutaRecurso;

    
    public Long getIdRecurso() {
        return idRecurso;
    }
    
    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public String getNombreRecurso() {
        return nombreRecurso;
    }
    
    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }
    
    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }
    
    public String getRutaRecurso() {
        return rutaRecurso;
    }
    
    public void setRutaRecurso(String rutaRecurso) {
        this.rutaRecurso = rutaRecurso;
    }
    
}
