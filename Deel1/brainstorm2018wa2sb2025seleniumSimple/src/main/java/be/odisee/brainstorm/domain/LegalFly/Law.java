package be.odisee.brainstorm.domain.LegalFly;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "law")
@Getter
@Setter
public class Law {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String referenceCode;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}