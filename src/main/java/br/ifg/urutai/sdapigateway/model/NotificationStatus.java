package br.ifg.urutai.sdapigateway.model;

public enum NotificationStatus {
    PENDING("Pendente"),
    SENT("Enviado"),
    FAILED("Falhou");

    private final String description;

    NotificationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
