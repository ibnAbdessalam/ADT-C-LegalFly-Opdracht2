package be.odisee.brainstorm.controller;

import be.odisee.brainstorm.domain.LegalFly.Request;
import be.odisee.brainstorm.service.LegalFly.LegalFlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private LegalFlyService legalFlyService;

    @GetMapping("/")
    public String dashboard(@RequestParam(name = "id", required = false) Integer id, Model model) {

        List<Request> requests = legalFlyService.getAllRequests();
        model.addAttribute("documents", requests);

        if (id != null) {
            Request selectedRequest = legalFlyService.getRequestById(id);
            model.addAttribute("selectedDocument", selectedRequest);

            if (selectedRequest != null) {
                model.addAttribute("client", selectedRequest.getJurist());
            }
        }

        return "home";
    }
}