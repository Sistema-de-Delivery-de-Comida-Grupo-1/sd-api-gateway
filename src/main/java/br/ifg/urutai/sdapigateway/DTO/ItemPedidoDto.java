package br.ifg.urutai.sdapigateway.DTO;

public class ItemPedidoDto {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private double preco;
    private int quantidade;

    public ItemPedidoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
