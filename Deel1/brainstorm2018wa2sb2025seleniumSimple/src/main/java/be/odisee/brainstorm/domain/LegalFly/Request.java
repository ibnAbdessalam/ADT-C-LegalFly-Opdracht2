package be.odisee.brainstorm.domain.LegalFly;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "jurist_id")
    private Jurist jurist;

    @ManyToOne
    @JoinColumn(name = "law_id")
    private Law law;

    private String content;
    private String status;

    @OneToMany(mappedBy = "request")
    private List<Report> reports;

    // GETTERS AND SETTERS 👇
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jurist getJurist() {
        return jurist;
    }

    public void setJurist(Jurist jurist) {
        this.jurist = jurist;
    }

    public Law getLaw() {
        return law;
    }

    public void setLaw(Law law) {
        this.law = law;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}