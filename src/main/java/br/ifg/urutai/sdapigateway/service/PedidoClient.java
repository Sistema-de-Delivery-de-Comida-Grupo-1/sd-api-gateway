package br.ifg.urutai.sdapigateway.service;

import br.ifg.urutai.sdapigateway.DTO.ListaTransacoesDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoCreteDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoResponseDTO;
import br.ifg.urutai.sdapigateway.DTO.SaldoDTO;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PedidoClient {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public PedidoClient(
            RestClient.Builder builder,
            DiscoveryClient discoveryClient) {

        this.restClient = builder.build();
        this.discoveryClient = discoveryClient;
    }

    private String getBaseUrl() {

        List<ServiceInstance> instances =
                discoveryClient.getInstances("SD-API-PEDIDO");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "MICROSSERVICO-PEDIDO não encontrado no Eureka");
        }

        ServiceInstance instance = instances.get(0);

        return String.format(
                "http://%s:%d/pedidos",
                instance.getHost(),
                instance.getPort()
        );
    }

    public PedidoResponseDTO criarPedido(PedidoCreteDTO dto) {

        return restClient.post()
                .uri(getBaseUrl())
                .body(dto)
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public PedidoResponseDTO pagarPedido(Long id) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/pagar")
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public PedidoResponseDTO estornarPedido(Long id, String motivo) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/estornar?motivo=" + motivo)
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public PedidoResponseDTO iniciarPreparo(Long id) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/iniciar-preparo")
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public PedidoResponseDTO finalizarPreparo(Long id) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/finalizar-preparo")
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public PedidoResponseDTO atualizar(Long id, PedidoCreteDTO dto) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id)
                .body(dto)
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public PedidoResponseDTO buscarPorId(Long id) {

        return restClient.get()
                .uri(getBaseUrl() + "/" + id)
                .retrieve()
                .body(PedidoResponseDTO.class);
    }

    public List<PedidoResponseDTO> listarTodos() {

        return restClient.get()
                .uri(getBaseUrl())
                .retrieve()
                .body(List.class);
    }

    public SaldoDTO consultarSaldo() {

        return restClient.get()
                .uri(getBaseUrl() + "/pagamentos/saldo")
                .retrieve()
                .body(SaldoDTO.class);
    }

    public ListaTransacoesDTO listarTransacoes(
            int pagina,
            int tamanho) {

        return restClient.get()
                .uri(getBaseUrl()
                        + "/pagamentos/transacoes"
                        + "?pagina=" + pagina
                        + "&tamanho=" + tamanho)
                .retrieve()
                .body(ListaTransacoesDTO.class);
    }

    public void remover(Long id) {

        restClient.delete()
                .uri(getBaseUrl() + "/" + id)
                .retrieve()
                .toBodilessEntity();
    }
}