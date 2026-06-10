package br.ifg.urutai.sdapigateway.DTO;

import java.io.Serializable;
import java.util.List;

public class PedidoEntregaRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long idCliente;
    private double valorTotal;
    private List<ItemPedidoDto> itens;
    private String status;

    public PedidoEntregaRequestDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemPedidoDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDto> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
