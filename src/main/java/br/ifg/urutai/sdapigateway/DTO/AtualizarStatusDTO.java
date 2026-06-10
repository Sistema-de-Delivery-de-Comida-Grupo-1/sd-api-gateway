package br.ifg.urutai.sdapigateway.DTO;

import java.io.Serializable;

public class AtualizarStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private PedidoStatus status;

    public AtualizarStatusDTO() {
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }
}
