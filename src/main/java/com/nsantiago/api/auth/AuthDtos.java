package com.nsantiago.api.auth;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {
    public record RegistroRequest(
            @NotBlank @Size(max = 100) String nombre,
            @NotBlank @Email String correo,
            @NotBlank @Size(min = 8, max = 72) String contrasena,
            @Size(max = 100) String primerApellido,
            @Size(max = 100) String segundoApellido,
            @JsonAlias({"telefono", "wspNum"}) @Size(max = 20) String wspNum,
            String comprobanteCorreo,
            String tokenFirebase
    ) {}

    public record LoginRequest(
            @NotBlank @Email String correo,
            @NotBlank String contrasena
    ) {}

    public record UsuarioLoginDto(Integer id, String correo, String nombre, String rol) {}

    public record LoginResponse(String token, UsuarioLoginDto usuario) {}

    public record UsuarioMeDto(
            Integer id,
            String nombre,
            String primerApellido,
            String segundoApellido,
            String correo,
            String telefono,
            String rol,
            Boolean activo,
            Boolean correoVerificado,
            Boolean telefonoVerificado,
            Boolean tieneFoto,
            String fotoVersion
    ) {}

    public record UpdateMeRequest(
            @Size(max = 100) String nombre,
            @Size(max = 100) String primerApellido,
            @Size(max = 100) String segundoApellido
    ) {}
}
