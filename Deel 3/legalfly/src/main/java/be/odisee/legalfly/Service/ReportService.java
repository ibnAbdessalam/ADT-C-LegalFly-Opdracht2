package be.odisee.legalfly.Service;

import be.odisee.legalfly.Domain.Report;

import java.util.List;

public interface ReportService {

    void createReportWithInfractions(
            String reportType,
            String reportContent,
            int requestId,
            int adminId,
            List<ReportServiceImpl.InfractionInput> infractionInputs
    );

    List<Report> getAllReports();
    void deleteReport(int reportId);
    void updateReport(Report report);
}
