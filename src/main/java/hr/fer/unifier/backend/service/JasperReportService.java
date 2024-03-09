package hr.fer.unifier.backend.service;

import org.springframework.http.ResponseEntity;

public interface JasperReportService {
    ResponseEntity<byte[]> getPdfReport(Integer dealId);
}
