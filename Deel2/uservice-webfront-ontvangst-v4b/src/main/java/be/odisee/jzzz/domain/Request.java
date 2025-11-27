package be.odisee.jzzz.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "requests")
public class Request {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank @Size(max = 255)
	private String title;

	@Lob
	private String description;

	// enum in DB: PENDING | IN_PROGRESS | COMPLETED | CANCELLED
	@NotBlank
	@Column(nullable = false)
	private String status = "PENDING";

	@NotBlank @Email
	@Column(name = "client_email", nullable = false, length = 255)
	private String clientEmail;

	@Column(name = "created_at", updatable = false, insertable = false)
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@PreUpdate
	public void onUpdate() { this.updatedAt = Instant.now(); }

	// getters/setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getClientEmail() { return clientEmail; }
	public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
