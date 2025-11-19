package be.odisee.brainstorm.domain.LegalFly;

import jakarta.persistence.*;

// niet verandert
@Entity
@DiscriminatorValue("Administrator")
public class SecurityAdministrator extends Rol {

    private static final long serialVersionUID = 1L;

    public SecurityAdministrator(){}

    public SecurityAdministrator(String status, String usernaam, Persoon persoon){
        super(status,usernaam,persoon);
    }

    public SecurityAdministrator(int id, String status, String usernaam, Persoon persoon){
        super(id,status,usernaam, persoon);
    }

    // nu nog niet @Override
    public String getType() {
        return "Administrator";
    }

}