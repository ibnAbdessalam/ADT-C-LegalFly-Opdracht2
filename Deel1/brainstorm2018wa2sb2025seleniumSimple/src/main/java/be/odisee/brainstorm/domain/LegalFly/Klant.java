package be.odisee.brainstorm.domain.LegalFly;

import java.util.List;

import jakarta.persistence.*;
// niet verandert

@Entity
@DiscriminatorValue("Klant")
public class Klant extends Rol {

    private static final long serialVersionUID = 1L;

    public Klant(){}

    public Klant(String status, String usernaam, Persoon persoon){
        super(status,usernaam,persoon);
    }

    public Klant(int id, String status, String usernaam, Persoon persoon){
        super(id,status,usernaam,persoon);
    }

}