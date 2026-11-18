package com.nsantiago.api.publicacion;

import com.nsantiago.api.auth.AuthService;
import com.nsantiago.api.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class PublicacionService {
    private final PublicacionRepository publicacionRepository;
    private final PublicacionFotoRepository fotoRepository;
    private final AuthService authService;
    private final Path uploadDir;

    public PublicacionService(
            PublicacionRepository publicacionRepository,
            PublicacionFotoRepository fotoRepository,
            AuthService authService,
            @Value("${app.upload-dir}") String uploadDir
    ) throws IOException {
        this.publicacionRepository = publicacionRepository;
        this.fotoRepository = fotoRepository;
        this.authService = authService;
        this.uploadDir = Path.of(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
    }

    public List<PublicacionDtos.PublicacionDto> listar() {
        return publicacionRepository.findByActivaTrueAndEstadoOrderByCreadoEnDesc("PUBLICADA")
                .stream().map(this::toDto).toList();
    }

    public List<PublicacionDtos.PublicacionDto> mias(String correo) {
        Usuario usuario = authService.current(correo);
        return publicacionRepository.findByAutorOrderByCreadoEnDesc(usuario)
                .stream().map(this::toDto).toList();
    }

    public PublicacionDtos.PublicacionDto detalle(Integer id) {
        return toDto(publicacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publicación no encontrada")));
    }

    @Transactional
    public PublicacionDtos.PublicacionDto crear(
            String correo,
            PublicacionDtos.CrearPublicacionRequest request
    ) {
        Usuario usuario = authService.current(correo);
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(request.titulo().trim());
        publicacion.setDescripcion(request.descripcion().trim());
        publicacion.setReglas(request.reglas());
        publicacion.setAutor(usuario);
        publicacion.setPrecio(BigDecimal.valueOf(request.precio()));
        publicacion.setWspNum(usuario.getWspNum());
        publicacion.setCalle(request.calle());
        publicacion.setNumero(request.numero());
        publicacion.setColonia(request.colonia());
        publicacion.setCodigoPostal(request.codigoPostal());
        publicacion.setLatitud(request.latitud());
        publicacion.setLongitud(request.longitud());
        publicacion.setModalidad(request.modalidad());
        publicacion.setTipo(request.tipo());
        publicacion.setSuperficie(request.superficie());
        publicacion.setUsoSuelo(request.usoSuelo());
        publicacion.setDormitorios(request.dormitorios());
        publicacion.setBanos(request.banos());
        publicacion.setCochera(request.cochera());
        publicacion.setPiso(request.piso());
        publicacion.setNiveles(request.niveles());
        publicacion.setAmueblado(Boolean.TRUE.equals(request.amueblado()));
        publicacion.setMascotas(Boolean.TRUE.equals(request.mascotas()));
        publicacion.setElevador(Boolean.TRUE.equals(request.elevador()));
        publicacion.setPatio(Boolean.TRUE.equals(request.patio()));
        publicacion.setEstado("PUBLICADA");
        publicacion.setActiva(true);
        return toDto(publicacionRepository.save(publicacion));
    }

    @Transactional
    public void eliminar(String correo, Integer id) {
        Usuario usuario = authService.current(correo);
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publicación no encontrada"));
        if (!publicacion.getAutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No puedes eliminar esta publicación");
        }
        publicacionRepository.delete(publicacion);
    }

    @Transactional
    public PublicacionDtos.PublicacionDto subirFoto(
            String correo,
            Integer id,
            MultipartFile archivo
    ) {
        Usuario usuario = authService.current(correo);
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publicación no encontrada"));
        if (!publicacion.getAutor().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No puedes modificar esta publicación");
        }

        String contentType = archivo.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Solo se permiten imágenes JPG o PNG");
        }
        if (archivo.isEmpty() || archivo.getSize() > 10 * 1024 * 1024) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "La foto debe pesar menos de 10 MB");
        }

        try {
            String extension = contentType.equals("image/png") ? ".png" : ".jpg";
            String filename = UUID.randomUUID() + extension;
            Files.copy(archivo.getInputStream(),
                    uploadDir.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            PublicacionFoto foto = new PublicacionFoto();
            foto.setPublicacion(publicacion);
            foto.setFotoUrl("/uploads/" + filename);
            foto.setOrden(publicacion.getFotos().size() + 1);
            fotoRepository.save(foto);
            publicacion.getFotos().add(foto);
            return toDto(publicacion);
        } catch (IOException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar la foto");
        }
    }

    public PublicacionFoto fotoPorId(Integer id) {
        return fotoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Foto no encontrada"));
    }

    public Path rutaArchivo(PublicacionFoto foto) {
        String name = Path.of(foto.getFotoUrl()).getFileName().toString();
        return uploadDir.resolve(name).normalize();
    }

    private PublicacionDtos.PublicacionDto toDto(Publicacion p) {
        Usuario vendedor = p.getAutor();
        List<Integer> fotos = p.getFotos().stream()
                .map(PublicacionFoto::getId).toList();
        long fecha = p.getCreadoEn().toEpochMilli();
        return new PublicacionDtos.PublicacionDto(
                p.getId(),
                p.getTitulo(),
                p.getDescripcion(),
                p.getReglas(),
                p.getPrecio().doubleValue(),
                p.getModalidad(),
                p.getTipo(),
                p.getEstado(),
                p.getActiva(),
                false,
                p.getCalle(),
                p.getNumero(),
                p.getColonia(),
                p.getCodigoPostal(),
                p.getLatitud(),
                p.getLongitud(),
                p.getSuperficie(),
                p.getUsoSuelo(),
                p.getDormitorios(),
                p.getBanos(),
                p.getCochera(),
                p.getPiso(),
                p.getNiveles(),
                p.getAmueblado(),
                p.getMascotas(),
                p.getElevador(),
                p.getPatio(),
                fotos,
                new PublicacionDtos.VendedorDto(
                        vendedor.getId(),
                        vendedor.getNombre(),
                        vendedor.getCorreo(),
                        vendedor.getWspNum(),
                        vendedor.getFoto() != null,
                        vendedor.getFoto()
                ),
                fecha,
                false
        );
    }
}
