package hr.fer.unifier.backend.resource;

import hr.fer.unifier.backend.service.JasperReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jasper-report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JasperReportResource {

    private final JasperReportService jasperReportService;

    @GetMapping("/volunteer-report/{dealId}")
    public ResponseEntity<byte[]> getPdfReport(@PathVariable Integer dealId){
        return jasperReportService.getPdfReport(dealId);
    }

    @GetMapping("/volunteer-contract/{dealId}")
    public ResponseEntity<byte[]> getVolunteerContract(@PathVariable Integer dealId){
        return jasperReportService.getVolunteerContract(dealId);
    }
}
