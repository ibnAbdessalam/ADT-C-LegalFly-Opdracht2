package be.odisee.legalfly.Domain;

import jakarta.persistence.*;

// Law klasse
@Entity
@Table(name = "law")
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "reference_code", nullable = false, unique = true)
    private String referenceCode;

    // Constructors
    public Law() {}

    public Law(String name, String description, String referenceCode) {
        this.name = name;
        this.description = description;
        this.referenceCode = referenceCode;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
