package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Law;
import be.odisee.legalfly.Domain.Request;
import be.odisee.legalfly.Service.LawService;
import be.odisee.legalfly.Service.LawServiceImpl;
import be.odisee.legalfly.Service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/jurist/requests")
public class RequestController {

    private final RequestService requestService;
    private final LawService lawService;

    public RequestController(RequestService requestService, LawServiceImpl lawService) {
        this.requestService = requestService;
        this.lawService = lawService;
    }

    @GetMapping
    public String showRequests(@RequestParam("juristId") int juristId, Model model) {
        List<Request> requests = requestService.getRequestsForJurist(juristId);
        model.addAttribute("requests", requests);
        model.addAttribute("juristId", juristId);
        return "Jurist/Requests";
    }

    @GetMapping("/new")
    public String showRequestForm(@RequestParam("juristId") int juristId, Model model) {
        List<Law> laws = lawService.getAllLaws();
        model.addAttribute("laws", laws);
        model.addAttribute("juristId", juristId);
        return "Jurist/RequestForm";
    }

    @GetMapping("/delete")
    public String deleteReport(@RequestParam int requestId,
                               @RequestParam int juristId) {
        requestService.deleteRequest(requestId);
        return "redirect:/jurist/requests?juristId=" + juristId;
    }

    @PostMapping
    public String createRequest(@RequestParam int juristId,
                                @RequestParam int lawId,
                                @RequestParam String content) {
        requestService.createRequest(juristId, lawId, content);
        return "redirect:/jurist/requests?juristId=" + juristId;
    }
}
