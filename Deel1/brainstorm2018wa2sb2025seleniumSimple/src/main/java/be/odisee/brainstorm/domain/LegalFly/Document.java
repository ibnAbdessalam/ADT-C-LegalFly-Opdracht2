package be.odisee.brainstorm.domain.LegalFly;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Table(name="documenten")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    // we willen dit even aan de db overlaten
    // specifieke noden FitLibrary zullen we met een functie oplossen
    // private static int nextid=1;

    @Column
    private documentState status;

    @Column
    private String title;

    @Column
    private String text;

    public Document() {}

    public Document(documentState status, String title, String text) {
        this.status = status;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {}
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public void setStatus(documentState status) { this.status = status; }

    public documentState getStatus() {
        return status;
    }
}
