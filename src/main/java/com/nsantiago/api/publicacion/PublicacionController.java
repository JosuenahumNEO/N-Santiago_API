package com.nsantiago.api.publicacion;

import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publicaciones")
public class PublicacionController {
    private final PublicacionService service;

    public PublicacionController(PublicacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PublicacionDtos.PublicacionDto> listar() {
        return service.listar();
    }

    @GetMapping("/mias")
    public List<PublicacionDtos.PublicacionDto> mias(Authentication authentication) {
        return service.mias(authentication.getName());
    }

    @GetMapping("/{id}")
    public PublicacionDtos.PublicacionDto detalle(@PathVariable Integer id) {
        return service.detalle(id);
    }

    @PostMapping
    public ResponseEntity<PublicacionDtos.PublicacionDto> crear(
            Authentication authentication,
            @Valid @RequestBody PublicacionDtos.CrearPublicacionRequest request
    ) {
        return ResponseEntity.status(201).body(
                service.crear(authentication.getName(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            Authentication authentication,
            @PathVariable Integer id
    ) {
        service.eliminar(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/fotos")
    public PublicacionDtos.PublicacionDto subirFoto(
            Authentication authentication,
            @PathVariable Integer id,
            @RequestPart("foto") MultipartFile foto
    ) {
        return service.subirFoto(authentication.getName(), id, foto);
    }

    @GetMapping("/fotos/{fotoId}")
    public ResponseEntity<Resource> foto(@PathVariable Integer fotoId) {
        PublicacionFoto foto = service.fotoPorId(fotoId);
        Path path = service.rutaArchivo(foto);
        try {
            Resource resource = new InputStreamResource(Files.newInputStream(path));
            String type = Files.probeContentType(path);
            return ResponseEntity.ok()
                    .contentType(type == null
                            ? MediaType.APPLICATION_OCTET_STREAM
                            : MediaType.parseMediaType(type))
                    .body(resource);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
