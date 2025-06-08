package br.com.fiap.globalsolution2025.entity.enums;

public enum STATUS {
     RESOLVIDO("resolvido"),
    ATIVO("ativo"),
    IGNORADO("ignorado");
    private final String status;

    STATUS(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
