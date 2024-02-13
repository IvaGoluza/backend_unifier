package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.service.JasperReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping(value = "/jasper-report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
@RequiredArgsConstructor
public class JasperReportResource {

    private final JasperReportService jasperReportService;

    @GetMapping("/volunteer-report")
    public ResponseEntity<StreamingResponseBody> getPdfReport(){
        return jasperReportService.getPdfReport();
    }
}
