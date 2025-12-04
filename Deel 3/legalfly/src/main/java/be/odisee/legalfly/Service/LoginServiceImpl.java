package be.odisee.legalfly.Service;

import be.odisee.legalfly.Dao.AdminRepository;
import be.odisee.legalfly.Dao.JuristRepository;
import be.odisee.legalfly.Domain.Admin;
import be.odisee.legalfly.Domain.Jurist;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AdminRepository adminRepository;
    private final JuristRepository juristRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginServiceImpl(AdminRepository adminRepository, JuristRepository juristRepository) {
        this.adminRepository = adminRepository;
        this.juristRepository = juristRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Admin loginAsAdmin(String email, String rawPassword) {
        return adminRepository.findAll().stream()
                .filter(a -> a.getEmail().equalsIgnoreCase(email) && passwordEncoder.matches(rawPassword, a.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ongeldige admin login"));
    }

    @Override
    public Jurist loginAsJurist(String email, String rawPassword) {
        return juristRepository.findAll().stream()
                .filter(j -> j.getEmail().equalsIgnoreCase(email) && passwordEncoder.matches(rawPassword, j.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ongeldige jurist login"));
    }
}
