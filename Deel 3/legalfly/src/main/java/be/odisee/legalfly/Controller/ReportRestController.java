package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Report;
import be.odisee.legalfly.Dto.CreateReportWithInfractionsRequest;
import be.odisee.legalfly.Service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {
    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAll() {
        return reportService.getAllReports();
    }

//    @GetMapping("/getById")
//    public Report getById(int id) {
//        return reportService.getById(id);
//    }

    @PostMapping
    public void createWithInfractions(@RequestBody CreateReportWithInfractionsRequest request) {
        reportService.createReportWithInfractions(request.reportType, request.reportContent, request.requestId, request.adminId, request.infractions);
    }
}
