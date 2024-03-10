package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.api.user.UserCardInfoDTO;
import hr.fer.unifier.backend.db.DealDao;
import hr.fer.unifier.backend.db.RecensionDao;
import hr.fer.unifier.backend.db.entity.Deal;
import hr.fer.unifier.backend.enums.Sender;
import hr.fer.unifier.backend.service.JasperReportService;
import hr.fer.unifier.backend.service.UserService;
import hr.fer.unifier.backend.util.file.StreamingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JasperReportServiceImpl implements JasperReportService {
    private final RecensionDao recensionDao;
    private final DealDao dealDao;
    private final DataSource dataSource;
    private final StreamingUtil streamingUtil;
    private final UserService userService;
    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<byte[]> getPdfReport(Integer dealId) {
        final Deal deal = dealDao.findById(Long.valueOf(dealId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dogovor s id %d ne postoji", dealId))
        );

        boolean recensionExists = recensionDao.existsByDeal(deal);
        if (!recensionExists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recenzija ne postoji!");
        }

        try (Connection connection = dataSource.getConnection()) {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("deal_id", dealId);

            final JasperPrint jasperPrint = fillReport(parameters, "jasper/certificateOfVolunteering.jasper", connection);
            return exportToPdfByteArray(jasperPrint, "potvrda-o-volontiranju.pdf");
        } catch (Exception e) {
            log.error("Fill report failed", e);
        }

        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<byte[]> getVolunteerContract(Integer dealId) {
        final Deal deal = dealDao.findById(Long.valueOf(dealId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dogovor s id %d ne postoji", dealId))
        );

        final Long volunteerId;
        if (deal.getSender().equals(Sender.VOLUNTEER)){
            volunteerId = deal.getSenderId().getId();
        }else {
            volunteerId = deal.getAdvert().getUser().getId();
        }
        final UserCardInfoDTO userInfo = userService.getUserCardInfo(volunteerId);
        final String volunteerName = userInfo.getName().replace(" ", "-");

        try (Connection connection = dataSource.getConnection()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("deal_id", dealId);

            final List<JasperPrint> jasperPrints = new ArrayList<>();

            jasperPrints.add(fillReport(map, "jasper/volunteerContractPart1.jasper", connection));
            jasperPrints.add(fillReport(map, "jasper/volunteerContractPart2.jasper", connection));
            jasperPrints.add(fillReport(map, "jasper/volunteerContractPart3.jasper", connection));

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints)); //Set as export input my list with JasperPrint s
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
            exporter.exportReport();

            final byte[] contract = out.toByteArray();

            String fileName = String.format("Ugovor-o-volontiranju-%s.pdf", volunteerName);
            return exportToPdfByteArray(contract, fileName);
        } catch (Exception e) {
            log.error("Fill report failed", e);
        }

        return null;
    }

    private JasperPrint fillReport(Map<String, Object> parameters, String fileName, Connection connection) throws JRException, IOException {
        final File report = new ClassPathResource(fileName).getFile();
        final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report);
        return JasperFillManager.fillReport(jasperReport, parameters, connection);
    }

    private static ResponseEntity<byte[]> exportToPdfByteArray(byte[] pdf, String fileName) throws JRException {
        final HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(URLEncoder.encode(fileName, StandardCharsets.UTF_8)).build());
        httpHeaders.setContentLength(pdf.length);

        return ResponseEntity.ok().headers(httpHeaders).body(pdf);
    }

    private static ResponseEntity<byte[]> exportToPdfByteArray(JasperPrint jasperPrint, String fileName) throws JRException {
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        return exportToPdfByteArray(pdf,fileName);
    }

    private ResponseEntity<StreamingResponseBody> exportToPdf(JasperPrint jasperPrint) throws JRException, SQLException {
        final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        final Blob pdfBlob = new SerialBlob(pdfBytes);
        return streamingUtil.getBlobStreamingResponse("users.pdf", pdfBlob);
    }
}
