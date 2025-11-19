package be.odisee.brainstorm.service.LegalFly;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class MockDataService {
    private final Random rnd = new Random();

    public String mockForField(String field) {
        String f = field == null ? "" : field.toLowerCase();
        return switch (f) {
            case "naam", "name", "clientname" -> "Emma Janssens";
            case "email", "e-mail", "clientemail" -> "anon" + (1000 + rnd.nextInt(9000)) + "@example.com";
            case "telefoon", "phone", "gsm" -> "+32" + (100000000 + rnd.nextInt(899999999));
            case "adres", "address" -> "Kerkstraat " + (1 + rnd.nextInt(200)) + ", Leuven";
            default -> "X" + (1000 + rnd.nextInt(9000));
        };
    }
}
