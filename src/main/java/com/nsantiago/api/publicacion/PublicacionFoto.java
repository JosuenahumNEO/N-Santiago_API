package com.nsantiago.api.publicacion;

import jakarta.persistence.*;

@Entity
@Table(name = "publicacion_foto")
public class PublicacionFoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    @Column(name = "foto_url", nullable = false, columnDefinition = "TEXT")
    private String fotoUrl;

    @Column(nullable = false)
    private Integer orden = 1;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Publicacion getPublicacion() { return publicacion; }
    public void setPublicacion(Publicacion publicacion) { this.publicacion = publicacion; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
