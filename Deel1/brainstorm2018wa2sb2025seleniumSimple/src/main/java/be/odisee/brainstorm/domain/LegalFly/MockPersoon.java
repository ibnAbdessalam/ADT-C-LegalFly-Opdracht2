package be.odisee.brainstorm.domain.LegalFly;

import java.util.List;
import jakarta.persistence.*;
// niet verandert

@Entity
@DiscriminatorValue("MockPersoon")
public class MockPersoon extends Rol {

    private static final long serialVersionUID = 1L;

    public MockPersoon(){}

    public MockPersoon(String status, String usernaam, Persoon persoon){
        super(status,usernaam,persoon);
    }

    public MockPersoon(int id, String status, String usernaam, Persoon persoon){
        super(id,status,usernaam,persoon);
    }

}