package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.service.JasperReportService;
import hr.fer.unifier.backend.util.StreamingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class JasperReportServiceImpl implements JasperReportService {

    private final DataSource dataSource;

    @Override
    public ResponseEntity<StreamingResponseBody> getPdfReport() {
        try (Connection connection = dataSource.getConnection()) {
            final File report = new ClassPathResource("jasper/users.jasper").getFile();
            final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report);

            //TODO: Za kasnije
            //JRParameter[] jr = jasperReport.getParameters();

            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<>(), connection);
            return exportToPdf(jasperPrint);
        } catch (Exception e) {
            log.error("Fill report failed", e);
        }

        return null;
    }

    private ResponseEntity<StreamingResponseBody> exportToPdf(JasperPrint jasperPrint) throws JRException, SQLException {
        final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        final Blob pdfBlob = new SerialBlob(pdfBytes);
        return StreamingUtil.getBlobStreamingResponse("users.pdf", pdfBlob);
    }
}
