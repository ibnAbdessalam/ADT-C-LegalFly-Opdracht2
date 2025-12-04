package be.odisee.legalfly.Service;

import be.odisee.legalfly.Dao.*;
import be.odisee.legalfly.Domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@Service
public class ReportServiceImpl implements ReportService {

    private final RequestRepository requestRepository;
    private final AdminRepository adminRepository;
    private final ReportRepository reportRepository;
    private final InfractionRepository infractionRepository;
    private final LawRepository lawRepository;

    public ReportServiceImpl(RequestRepository requestRepository,
                             AdminRepository adminRepository,
                             ReportRepository reportRepository,
                             InfractionRepository infractionRepository,
                             LawRepository lawRepository) {
        this.requestRepository = requestRepository;
        this.adminRepository = adminRepository;
        this.reportRepository = reportRepository;
        this.infractionRepository = infractionRepository;
        this.lawRepository = lawRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createReportWithInfractions(String reportType, String reportContent, int requestId, int adminId, List<InfractionInput> infractionInputs) {
        Request request = getRequestById(requestId);
        Admin admin = getAdminById(adminId);

        Report report = createReport(reportType, reportContent, request, admin);
        Report savedReport = reportRepository.save(report);

        List<Infraction> infractions = buildInfractions(infractionInputs, savedReport);

        CompositeReport compositeReport = new CompositeReport();
        for (Infraction infraction : infractions) {
            compositeReport.addComponent(infraction);
        }

        compositeReport.validate();

        saveInfractions(infractions);

        request.setStatus("reviewed");
        requestRepository.save(request);
    }

    private Request getRequestById(int requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request niet gevonden met ID: " + requestId));
    }

    private Admin getAdminById(int adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin niet gevonden met ID: " + adminId));
    }

    private Report createReport(String type, String content, Request request, Admin admin) {
        Report report = new Report();
        report.setRequest(request);
        report.setAdmin(admin);
        report.setType(type);
        report.setContent(content);
        report.setStatus("in progress");
        report.setVersionNumber(1);
        report.setNotificationStatus("todo");
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());
        return report;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void deleteReport(int reportId) {
        infractionRepository.deleteAll(
                infractionRepository.findAll().stream()
                        .filter(i -> i.getReport().getId() == reportId)
                        .toList()
        );
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report niet gevonden met ID: " + reportId));
        reportRepository.delete(report);
    }

    public void updateReport(Report report) {
        if (reportRepository.existsById(report.getId())) {
            report.setUpdatedAt(LocalDateTime.now());
            reportRepository.save(report);
        } else {
            throw new RuntimeException("Report niet gevonden met ID: " + report.getId());
        }
    }

    private List<Infraction> buildInfractions(List<InfractionInput> inputs, Report report) {
        List<Infraction> infractions = new ArrayList<>();
        for (InfractionInput input : inputs) {
            Law law = lawRepository.findById(input.lawId())
                    .orElseThrow(() -> new RuntimeException("Wet niet gevonden met ID: " + input.lawId()));

            Infraction infraction = new Infraction();
            infraction.setLaw(law);
            infraction.setType(input.type());
            infraction.setDetails(input.details());
            infraction.setCorrectiveMeasure(input.correctiveMeasure());
            infraction.setReport(report);

            infractions.add(infraction);
        }
        return infractions;
    }

    private void saveInfractions(List<Infraction> infractions) {
        for (Infraction infraction : infractions) {
            infractionRepository.save(infraction);
        }
    }

    public record InfractionInput(int lawId, String type, String details, String correctiveMeasure) {
    }
}
