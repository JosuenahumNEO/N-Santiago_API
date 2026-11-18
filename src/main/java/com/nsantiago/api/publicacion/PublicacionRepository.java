package com.nsantiago.api.publicacion;

import com.nsantiago.api.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    List<Publicacion> findByActivaTrueAndEstadoOrderByCreadoEnDesc(String estado);
    List<Publicacion> findByAutorOrderByCreadoEnDesc(Usuario autor);
}
