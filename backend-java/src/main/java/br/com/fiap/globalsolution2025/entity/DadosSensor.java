package br.com.fiap.globalsolution2025.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ssx_leituras_sensores")
public class DadosSensor {
    @Id
    @Column(name = "id_leitura_sensor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "temperatura")
    private Double temperatura;
    @Column(name = "umidade")
    private Double umidade;
    @Column(name = "radiacao")
    private Double indiceUv;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    @ManyToOne
    @JoinColumn(name = "ssx_usuarios_id_usuario")
    private Usuario usuario;

    public DadosSensor() {
    }

    public DadosSensor(Long id, Double temperatura, Double umidade, Double indiceUv, LocalDateTime dataHora) {
        this.id = id;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.indiceUv = indiceUv;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getUmidade() {
        return umidade;
    }

    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }

    public Double getIndiceUv() {
        return indiceUv;
    }

    public void setIndiceUv(Double indiceUv) {
        this.indiceUv = indiceUv;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

