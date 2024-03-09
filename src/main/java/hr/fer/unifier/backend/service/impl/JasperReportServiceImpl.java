package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.service.JasperReportService;
import hr.fer.unifier.backend.util.file.StreamingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class JasperReportServiceImpl implements JasperReportService {

    private final DataSource dataSource;
    private final StreamingUtil streamingUtil;

    @Override
    public ResponseEntity<byte[]> getPdfReport(Integer dealId) {
        try (Connection connection = dataSource.getConnection()) {
//            final InputStream stream= new ClassPathResource("jasper/users.jrxml").getInputStream();
//            final JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(stream);

            final File report = new ClassPathResource("jasper/users.jasper").getFile();
            final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report);

            HashMap<String, Object> map = new HashMap<>();
            map.put("deal_id", dealId);
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
            //return exportToPdf(jasperPrint);
            final HttpHeaders httpHeaders = new HttpHeaders();


            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(URLEncoder.encode("users.pdf", StandardCharsets.UTF_8)).build());
            httpHeaders.setContentLength(pdf.length);

            return ResponseEntity.ok().headers(httpHeaders).body(pdf);
        } catch (Exception e) {
            log.error("Fill report failed", e);
        }

        return null;
    }

    private ResponseEntity<StreamingResponseBody> exportToPdf(JasperPrint jasperPrint) throws JRException, SQLException {
        final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        final Blob pdfBlob = new SerialBlob(pdfBytes);
        return streamingUtil.getBlobStreamingResponse("users.pdf", pdfBlob);
    }
}
