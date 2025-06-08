package br.com.fiap.globalsolution2025.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "ssx_emails")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_email")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private boolean ativo;

    public Email() {}

    public Email(String email, boolean ativo) {
        this.email = email;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return email;
    }
}
