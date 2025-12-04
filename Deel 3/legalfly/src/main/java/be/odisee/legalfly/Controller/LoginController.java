package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Admin;
import be.odisee.legalfly.Domain.Jurist;
import be.odisee.legalfly.Service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login"; // templates/login.html
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String role,
                              @RequestParam String email,
                              @RequestParam String password,
                              Model model,
                              HttpSession session) {
        try {
            if (role.equals("admin")) {
                Admin admin = loginService.loginAsAdmin(email, password);
                session.setAttribute("admin", admin);
                return "redirect:/admin/requests?adminId=" + admin.getId();
            } else if (role.equals("jurist")) {
                Jurist jurist = loginService.loginAsJurist(email, password);
                session.setAttribute("jurist", jurist);
                return "redirect:/jurist/requests?juristId=" + jurist.getId();
            } else {
                throw new RuntimeException("Ongeldige rol");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Login mislukt: " + e.getMessage());
            return "login";
        }
    }
}
