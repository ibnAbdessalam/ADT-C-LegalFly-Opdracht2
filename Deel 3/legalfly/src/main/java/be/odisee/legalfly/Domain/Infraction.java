package be.odisee.legalfly.Domain;

import be.odisee.legalfly.Service.ReportComponent;
import jakarta.persistence.*;

@Entity
@Table(name = "infraction")
public class Infraction implements ReportComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @ManyToOne
    @JoinColumn(name = "law_id", nullable = false)
    private Law law;

    @Column(nullable = false, length = 100)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;

    @Column(name = "corrective_measure", columnDefinition = "TEXT")
    private String correctiveMeasure;

    // ===== Composite Pattern: Methods from ReportComponent =====

    @Override
    public void validate() {
        if (law == null || type == null || details == null || type.isBlank() || details.isBlank()) {
            throw new IllegalArgumentException("Infraction bevat ongeldige of onvolledige gegevens.");
        }
    }

    @Override
    public String summarize() {
        return "Inbreuk op " + (law != null ? law.getName() : "onbekende wet") + ": " + details;
    }

    // ===== Constructors =====

    public Infraction() {}

    public Infraction(Report report, Law law, String type, String details, String correctiveMeasure) {
        this.report = report;
        this.law = law;
        this.type = type;
        this.details = details;
        this.correctiveMeasure = correctiveMeasure;
    }

    // ===== Getters & Setters =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Law getLaw() {
        return law;
    }

    public void setLaw(Law law) {
        this.law = law;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCorrectiveMeasure() {
        return correctiveMeasure;
    }

    public void setCorrectiveMeasure(String correctiveMeasure) {
        this.correctiveMeasure = correctiveMeasure;
    }
}