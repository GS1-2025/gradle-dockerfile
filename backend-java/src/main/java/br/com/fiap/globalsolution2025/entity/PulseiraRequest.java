package br.com.fiap.globalsolution2025.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ssx_pulseira_request")

public class PulseiraRequest {
    @Id
    @Column(name = "id_pulseira_request")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceToken;

    @ManyToOne
    @JoinColumn(name = "ssx_usuarios_id_usuario")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PulseiraRequest() {
    }

    public PulseiraRequest(Long id, String deviceToken, Usuario usuario) {
        this.id = id;
        this.deviceToken = deviceToken;
        this.usuario = usuario;
    }
}

