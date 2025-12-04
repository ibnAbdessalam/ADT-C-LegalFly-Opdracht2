package be.odisee.legalfly.Controller;

import be.odisee.legalfly.Domain.Admin;
import be.odisee.legalfly.Domain.Jurist;
import be.odisee.legalfly.Dto.LoginRequest;
import be.odisee.legalfly.Service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class LoginRestController {
    private final LoginService loginService;

    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login-admin")
    public Admin loginAsAdmin(@RequestBody LoginRequest request) {
        return loginService.loginAsAdmin(request.email, request.password);
    }

    @PostMapping("/login-jurist")
    public Jurist loginAsJurist(@RequestBody LoginRequest request) {
        return loginService.loginAsJurist(request.email, request.password);
    }
}
