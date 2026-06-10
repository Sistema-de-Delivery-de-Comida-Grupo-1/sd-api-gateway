package br.ifg.urutai.sdapigateway.DTO;

public class UsuarioResumoDTO {
    private Long id;
    private String nome;

    public UsuarioResumoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
