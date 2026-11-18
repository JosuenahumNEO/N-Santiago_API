package com.nsantiago.api.publicacion;

import com.nsantiago.api.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publicacion")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Integer id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String reglas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor", nullable = false)
    private Usuario autor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "wsp_num", nullable = false, length = 20)
    private String wspNum;

    private String calle;
    private String numero;
    private String colonia;
    @Column(name = "codigo_postal") private String codigoPostal;
    private Double latitud;
    private Double longitud;
    private String modalidad;
    private String tipo;
    private String estado = "PUBLICADA";
    private Boolean activa = true;
    private Integer superficie;
    @Column(name = "uso_suelo") private String usoSuelo;
    private Integer dormitorios;
    private Integer banos;
    private Integer cochera;
    private Integer piso;
    private Integer niveles;
    private Boolean amueblado = false;
    private Boolean mascotas = false;
    private Boolean elevador = false;
    private Boolean patio = false;

    @Column(name = "creado_en", nullable = false)
    private Instant creadoEn = Instant.now();

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<PublicacionFoto> fotos = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getReglas() { return reglas; }
    public void setReglas(String reglas) { this.reglas = reglas; }
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public String getWspNum() { return wspNum; }
    public void setWspNum(String wspNum) { this.wspNum = wspNum; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
    public Integer getSuperficie() { return superficie; }
    public void setSuperficie(Integer superficie) { this.superficie = superficie; }
    public String getUsoSuelo() { return usoSuelo; }
    public void setUsoSuelo(String usoSuelo) { this.usoSuelo = usoSuelo; }
    public Integer getDormitorios() { return dormitorios; }
    public void setDormitorios(Integer dormitorios) { this.dormitorios = dormitorios; }
    public Integer getBanos() { return banos; }
    public void setBanos(Integer banos) { this.banos = banos; }
    public Integer getCochera() { return cochera; }
    public void setCochera(Integer cochera) { this.cochera = cochera; }
    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }
    public Integer getNiveles() { return niveles; }
    public void setNiveles(Integer niveles) { this.niveles = niveles; }
    public Boolean getAmueblado() { return amueblado; }
    public void setAmueblado(Boolean amueblado) { this.amueblado = amueblado; }
    public Boolean getMascotas() { return mascotas; }
    public void setMascotas(Boolean mascotas) { this.mascotas = mascotas; }
    public Boolean getElevador() { return elevador; }
    public void setElevador(Boolean elevador) { this.elevador = elevador; }
    public Boolean getPatio() { return patio; }
    public void setPatio(Boolean patio) { this.patio = patio; }
    public Instant getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
    public List<PublicacionFoto> getFotos() { return fotos; }
    public void setFotos(List<PublicacionFoto> fotos) { this.fotos = fotos; }
}
