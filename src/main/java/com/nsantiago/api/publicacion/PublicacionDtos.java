package com.nsantiago.api.publicacion;

import jakarta.validation.constraints.*;

import java.util.List;

public class PublicacionDtos {
    public record CrearPublicacionRequest(
            @NotBlank @Size(max = 150) String titulo,
            @NotBlank @Size(max = 5000) String descripcion,
            @Size(max = 3000) String reglas,
            @NotNull @DecimalMin("0.01") @DecimalMax("9999999999") Double precio,
            @NotBlank String modalidad,
            @NotBlank String tipo,
            @Size(max = 150) String calle,
            @Size(max = 20) String numero,
            @Size(max = 150) String colonia,
            @Size(max = 10) String codigoPostal,
            Double latitud,
            Double longitud,
            Integer superficie,
            String usoSuelo,
            Integer dormitorios,
            Integer banos,
            Integer cochera,
            Integer piso,
            Integer niveles,
            Boolean amueblado,
            Boolean mascotas,
            Boolean elevador,
            Boolean patio
    ) {}

    public record VendedorDto(
            Integer id,
            String nombre,
            String correo,
            String telefono,
            Boolean tieneFoto,
            String fotoVersion
    ) {}

    public record PublicacionDto(
            Integer id,
            String titulo,
            String descripcion,
            String reglas,
            Double precio,
            String modalidad,
            String tipo,
            String estado,
            Boolean activa,
            Boolean desactivadaPorAdmin,
            String calle,
            String numero,
            String colonia,
            String codigoPostal,
            Double latitud,
            Double longitud,
            Integer superficie,
            String usoSuelo,
            Integer dormitorios,
            Integer banos,
            Integer cochera,
            Integer piso,
            Integer niveles,
            Boolean amueblado,
            Boolean mascotas,
            Boolean elevador,
            Boolean patio,
            List<Integer> fotos,
            VendedorDto vendedor,
            Long fechaRegistro,
            Boolean guardada
    ) {}
}
