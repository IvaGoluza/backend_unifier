package hr.fer.unifier.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface JasperReportService {
    ResponseEntity<StreamingResponseBody> getPdfReport();
}
