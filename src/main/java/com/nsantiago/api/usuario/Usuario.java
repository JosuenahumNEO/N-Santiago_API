package com.nsantiago.api.usuario;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(columnDefinition = "TEXT")
    private String foto;

    @Column(name = "wsp_num", nullable = false, length = 20)
    private String wspNum;

    @Column(nullable = false, length = 30)
    private String rol = "VENDEDOR";

    @Column(name = "creado_en", nullable = false)
    private Instant creadoEn = Instant.now();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    public String getWspNum() { return wspNum; }
    public void setWspNum(String wspNum) { this.wspNum = wspNum; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Instant getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}
