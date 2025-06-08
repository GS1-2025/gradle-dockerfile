package br.com.fiap.globalsolution2025.entity.enums;

public enum TIPO {
    TEMPERATURA_ALTA("temperatura_alta"),
    UMIDADE_BAIXA("umidade_baixa"),
    UV_EXTREMO("uv_extremo"),
    NENHUM("nenhum");
    private final String tipo;

    TIPO(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
