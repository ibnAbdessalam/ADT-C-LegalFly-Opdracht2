package be.odisee.legalfly.Service;

import be.odisee.legalfly.Domain.Admin;
import be.odisee.legalfly.Domain.Jurist;

public interface LoginService {

    Admin loginAsAdmin(String email, String rawPassword);

    Jurist loginAsJurist(String email, String rawPassword);
}