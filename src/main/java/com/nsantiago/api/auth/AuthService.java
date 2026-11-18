package com.nsantiago.api.auth;

import com.nsantiago.api.usuario.Usuario;
import com.nsantiago.api.usuario.UsuarioRepository;
import com.nsantiago.api.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthDtos.LoginResponse registrar(AuthDtos.RegistroRequest request) {
        String correo = request.correo().trim().toLowerCase();
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        String apellidoCompleto = String.join(" ",
                        request.primerApellido() == null ? "" : request.primerApellido().trim(),
                        request.segundoApellido() == null ? "" : request.segundoApellido().trim())
                .trim();
        usuario.setNombre(String.join(" ",
                        request.nombre().trim(),
                        apellidoCompleto).trim());
        usuario.setCorreo(correo);
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));
        String wspNum = request.wspNum() == null ? "" : request.wspNum().trim();
        usuario.setWspNum(wspNum);
        usuario.setRol("VENDEDOR");
        usuario = usuarioRepository.save(usuario);

        return loginResponse(usuario);
    }

    public AuthDtos.LoginResponse login(AuthDtos.LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.correo().trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));
        if (!passwordEncoder.matches(request.contrasena(), usuario.getContrasena())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
        return loginResponse(usuario);
    }

    private AuthDtos.LoginResponse loginResponse(Usuario usuario) {
        return new AuthDtos.LoginResponse(
                jwtService.generate(usuario),
                new AuthDtos.UsuarioLoginDto(
                        usuario.getId(),
                        usuario.getCorreo(),
                        usuario.getNombre(),
                        usuario.getRol()
                )
        );
    }

    public Usuario current(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Usuario no encontrado"));
    }
}
