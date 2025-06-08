package br.com.fiap.globalsolution2025.entity;

import br.com.fiap.globalsolution2025.entity.enums.STATUS;
import br.com.fiap.globalsolution2025.entity.enums.TIPO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ssx_alertas")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alerta")
    private TIPO tipo;

    private String mensagem;

    @Column(name = "nivel_risco")
    private Integer nivelRisco;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private STATUS status;

    @ManyToOne
    @JoinColumn(name = "ssx_ltrs_id_leitura_sensor")
    private DadosSensor leituraSensor;



    public Alerta() {
    }

    public Alerta(Long id, TIPO tipo, String mensagem, Integer nivelRisco, STATUS status, DadosSensor leituraSensor) {
        this.id = id;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.nivelRisco = nivelRisco;
        this.status = status;
        this.leituraSensor = leituraSensor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(Integer nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public DadosSensor getLeituraSensor() {
        return leituraSensor;
    }

    public void setLeituraSensor(DadosSensor leituraSensor) {
        this.leituraSensor = leituraSensor;
    }


}
