package hr.fer.unifier.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.function.Supplier;

@Slf4j
public class StreamingUtil {

    public static ResponseEntity<StreamingResponseBody> getBlobStreamingResponse(final String fileName, final Supplier<Blob> supplier) {
        final HttpHeaders httpHeaders = new HttpHeaders();

        try{
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(URLEncoder.encode(fileName, StandardCharsets.UTF_8)).build());
            httpHeaders.setContentLength(supplier.get().length());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't set headers for response", e);
        }


        StreamingResponseBody streamingResponseBody = out -> {
            final Blob document = supplier.get();

            try (InputStream inputStream = document.getBinaryStream()) {
                inputStream.transferTo(out);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        return ResponseEntity.ok().headers(httpHeaders).body(streamingResponseBody);
    }

}
