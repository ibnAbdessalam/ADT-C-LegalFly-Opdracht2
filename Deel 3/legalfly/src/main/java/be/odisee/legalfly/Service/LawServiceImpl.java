package be.odisee.legalfly.Service;

import be.odisee.legalfly.Dao.LawRepository;
import be.odisee.legalfly.Domain.Law;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LawServiceImpl implements LawService {

    private final LawRepository lawRepository;

    public LawServiceImpl(LawRepository lawRepository) {
        this.lawRepository = lawRepository;
    }

    @Override
    public List<Law> getAllLaws() {
        return lawRepository.findAll(); // ✅ werkt nu op instantie
    }
}
