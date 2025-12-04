package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Law;
import be.odisee.legalfly.Domain.Report;
import be.odisee.legalfly.Domain.Request;
import be.odisee.legalfly.Service.LawService;
import be.odisee.legalfly.Service.ReportService;
import be.odisee.legalfly.Service.ReportServiceImpl;
import be.odisee.legalfly.Service.RequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReportController {

    private final RequestService requestService;
    private final ReportService reportService;
    private final LawService lawService;

    public ReportController(RequestService requestService,
                            ReportService reportService,
                            LawService lawService) {
        this.requestService = requestService;
        this.reportService = reportService;
        this.lawService = lawService;
    }

    @GetMapping("/requests")
    public String showAllRequests(@RequestParam("adminId") int adminId, Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        model.addAttribute("adminId", adminId);
        model.addAttribute("reports", reportService.getAllReports());
        return "Admin/AdminRequests";
    }

    @GetMapping("/reports/create")
    public String showReportForm(@RequestParam int requestId,
                                 @RequestParam int adminId,
                                 Model model) {
        Request request = requestService.getRequestById(requestId);
        List<Law> laws = lawService.getAllLaws();

        model.addAttribute("request", request);
        model.addAttribute("adminId", adminId);
        model.addAttribute("laws", laws);
        return "Admin/ReportForm";
    }

    @PutMapping("/reports/update")
    public String updateReport(@RequestParam int reportId,
                               @RequestParam String reportType,
                               @RequestParam String reportContent,
                               @RequestParam int requestId,
                               @RequestParam int adminId,
                               @RequestParam("infractionsJson") String infractionsJson) {
        // Récupère le rapport existant
        Report report = reportService.getAllReports().stream()
                .filter(r -> r.getId() == reportId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Mets à jour les champs
        report.setType(reportType);
        report.setContent(reportContent);
        // (Tu peux aussi gérer les infractions ici si besoin)

        reportService.updateReport(report);

        return "redirect:/admin/requests?adminId=" + adminId;
    }

    @GetMapping("/reports/delete")
    public String deleteReport(@RequestParam int reportId,
                               @RequestParam int adminId) {
        reportService.deleteReport(reportId);
        return "redirect:/admin/requests?adminId=" + adminId;
    }

    @PostMapping("/reports/save")
    public String saveReport(@RequestParam String reportType,
                             @RequestParam String reportContent,
                             @RequestParam int requestId,
                             @RequestParam int adminId,
                             @RequestParam("infractionsJson") String infractionsJson) {

        List<ReportServiceImpl.InfractionInput> infractions;
        try {
            ObjectMapper mapper = new ObjectMapper();
            infractions = Arrays.asList(
                    mapper.readValue(infractionsJson, ReportServiceImpl.InfractionInput[].class)
            );
        } catch (Exception e) {
            throw new RuntimeException("Fout bij het verwerken van overtredingen: " + e.getMessage());
        }

        reportService.createReportWithInfractions(
                reportType, reportContent, requestId, adminId, infractions
        );

        return "redirect:/admin/requests?adminId=" + adminId;
    }
}
