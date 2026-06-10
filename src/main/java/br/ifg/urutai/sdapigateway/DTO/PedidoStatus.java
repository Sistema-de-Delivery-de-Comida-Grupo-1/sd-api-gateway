package br.ifg.urutai.sdapigateway.DTO;

public enum PedidoStatus {
    RECEBIDO("Pedido Recebido"),
    PREPARANDO_ENTREGA("Preparando para Entrega"),
    SAIU_PARA_ENTREGA("Saiu para Entrega"),
    ENTREGUE("Entregue"),
    CONFIRMADO_PELO_CLIENTE("Confirmado pelo Cliente");

    private final String descricao;

    PedidoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
