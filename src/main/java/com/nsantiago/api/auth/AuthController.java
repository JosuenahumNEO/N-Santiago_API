package com.nsantiago.api.auth;

import com.nsantiago.api.usuario.Usuario;
import com.nsantiago.api.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthService authService, UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/auth/registro")
    public ResponseEntity<AuthDtos.LoginResponse> registrar(
            @Valid @RequestBody AuthDtos.RegistroRequest request
    ) {
        return ResponseEntity.status(201).body(authService.registrar(request));
    }

    @PostMapping("/auth/login")
    public AuthDtos.LoginResponse login(
            @Valid @RequestBody AuthDtos.LoginRequest request
    ) {
        return authService.login(request);
    }

    @GetMapping("/usuarios/me")
    public AuthDtos.UsuarioMeDto me(Authentication authentication) {
        Usuario usuario = authService.current(authentication.getName());
        return toMe(usuario);
    }

    @PatchMapping("/usuarios/me")
    public AuthDtos.UsuarioMeDto updateMe(
            Authentication authentication,
            @Valid @RequestBody AuthDtos.UpdateMeRequest request
    ) {
        Usuario usuario = authService.current(authentication.getName());
        if (request.nombre() != null) usuario.setNombre(request.nombre().trim());
        usuarioRepository.save(usuario);
        return toMe(usuario);
    }

    private AuthDtos.UsuarioMeDto toMe(Usuario usuario) {
        return new AuthDtos.UsuarioMeDto(
                usuario.getId(),
                usuario.getNombre(),
                "",
                null,
                usuario.getCorreo(),
                usuario.getWspNum(),
                usuario.getRol(),
                true,
                true,
                true,
                usuario.getFoto() != null,
                usuario.getFoto()
        );
    }

    @PostMapping("/auth/check-telefono")
    public ResponseEntity<Void> checkTelefono(@RequestBody java.util.Map<String, String> body) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/solicitar-codigo-correo")
    public ResponseEntity<Void> solicitarCodigo() {
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/auth/verificar-codigo-correo")
    public java.util.Map<String, String> verificarCodigo() {
        return java.util.Map.of("comprobanteCorreo", "verificado");
    }
}
