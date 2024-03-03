package hr.fer.unifier.backend.util.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

@Slf4j
@Component
public class StreamingUtil {

    public ResponseEntity<StreamingResponseBody> getBlobStreamingResponse(final String fileName, final Blob blobDocument) {
        final HttpHeaders httpHeaders = new HttpHeaders();

        try{
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(URLEncoder.encode(fileName, StandardCharsets.UTF_8)).build());
            httpHeaders.setContentLength(blobDocument.length());
        }catch (Exception e){
            log.error("Couldn't set headers for response", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't set headers for response", e);
        }


        StreamingResponseBody streamingResponseBody = out -> {
            try (InputStream inputStream = blobDocument.getBinaryStream()) {
                inputStream.transferTo(out);
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        };

        return ResponseEntity.ok().headers(httpHeaders).body(streamingResponseBody);
    }

}
