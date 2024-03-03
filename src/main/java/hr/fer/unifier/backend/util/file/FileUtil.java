package hr.fer.unifier.backend.util.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;

public class FileUtil {
    private static final List<String> validImageExtensions = List.of(
            "jpg", "jpeg", "png", "svg", "jfif", "pjpeg", "pjp"
    );

    public static void validateImage(MultipartFile file) {
        if (file.getContentType() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Datoteka ne sadrži tip!");
        }

        boolean validExtension = validImageExtensions
                .stream()
                .anyMatch(extension -> file.getContentType().endsWith(extension));

        if (!validExtension) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Slika nije u prikladnom obliku! Očekujemo png, jpg, jpeg i svg.");
        }
    }

    public static void validateHealthCertificate(MultipartFile file){
        if (file.getContentType() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Datoteka ne sadrži tip!");
        }

        if (!file.getContentType().endsWith("pdf")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Datoteka mora biti u pdf obliku!");
        }
    }

    public static String convertToBase64(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }
}
