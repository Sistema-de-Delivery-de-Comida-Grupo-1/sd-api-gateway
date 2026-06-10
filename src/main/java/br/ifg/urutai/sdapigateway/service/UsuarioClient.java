package br.ifg.urutai.sdapigateway.service;

import br.ifg.urutai.sdapigateway.DTO.LoginDTO;
import br.ifg.urutai.sdapigateway.DTO.UsuarioCadastroDTO;
import br.ifg.urutai.sdapigateway.DTO.UsuarioResumoDTO;
import br.ifg.urutai.sdapigateway.model.Usuario;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class UsuarioClient {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public UsuarioClient(
            RestClient.Builder builder,
            DiscoveryClient discoveryClient) {

        this.restClient = builder.build();
        this.discoveryClient = discoveryClient;
    }

    private String getBaseUrl() {

        List<ServiceInstance> instances =
                discoveryClient.getInstances("MICROSSERVICO-USUARIOS");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "MICROSSERVICO-USUARIOS não encontrado no Eureka");
        }

        ServiceInstance instance = instances.getFirst();

        return String.format(
                "http://%s:%d/usuarios",
                instance.getHost(),
                instance.getPort()
        );
    }

    public Usuario buscarPorId(Long id) {

        return restClient.get()
                .uri(getBaseUrl() + "/" + id)
                .retrieve()
                .body(Usuario.class);
    }

    public List<Usuario> listarTodos() {

        return restClient.get()
                .uri(getBaseUrl())
                .retrieve()
                .body(List.class);
    }

    public Usuario cadastrar(UsuarioCadastroDTO dto) {

        return restClient.post()
                .uri(getBaseUrl())
                .body(dto)
                .retrieve()
                .body(Usuario.class);
    }

    public Usuario atualizar(Long id, UsuarioCadastroDTO dto) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id)
                .body(dto)
                .retrieve()
                .body(Usuario.class);
    }

    public void deletar(Long id) {

        restClient.delete()
                .uri(getBaseUrl() + "/" + id)
                .retrieve()
                .toBodilessEntity();
    }

    public String realizarLogin(LoginDTO dto) {

        return restClient.post()
                .uri(getBaseUrl() + "/login")
                .body(dto)
                .retrieve()
                .body(String.class);
    }

    public UsuarioResumoDTO buscarResumo(Long id) {

        return restClient.get()
                .uri(getBaseUrl() + "/" + id + "/resumo")
                .retrieve()
                .body(UsuarioResumoDTO.class);
    }
}