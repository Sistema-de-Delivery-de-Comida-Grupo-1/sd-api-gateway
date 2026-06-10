package br.ifg.urutai.sdapigateway.service;

import br.ifg.urutai.sdapigateway.DTO.AtualizarStatusDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoEntregaRequestDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoEntregaResponseDTO;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class EntregaClient {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public EntregaClient(
            RestClient.Builder builder,
            DiscoveryClient discoveryClient) {

        this.restClient = builder.build();
        this.discoveryClient = discoveryClient;
    }

    private String getBaseUrl() {

        List<ServiceInstance> instances =
                discoveryClient.getInstances("SD-API-ENTREGA");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "SD-API-ENTREGA não encontrado no Eureka");
        }

        ServiceInstance instance = instances.get(0);

        return String.format(
                "http://%s:%d/api/entregas",
                instance.getHost(),
                instance.getPort()
        );
    }

    public PedidoEntregaResponseDTO criarPedido(
            PedidoEntregaRequestDTO dto) {

        return restClient.post()
                .uri(getBaseUrl())
                .body(dto)
                .retrieve()
                .body(PedidoEntregaResponseDTO.class);
    }

    public PedidoEntregaResponseDTO buscarPorId(Long id) {

        return restClient.get()
                .uri(getBaseUrl() + "/" + id)
                .retrieve()
                .body(PedidoEntregaResponseDTO.class);
    }

    public List<PedidoEntregaResponseDTO> listarTodos() {

        return restClient.get()
                .uri(getBaseUrl())
                .retrieve()
                .body(List.class);
    }

    public PedidoEntregaResponseDTO atualizarStatus(
            Long id,
            AtualizarStatusDTO dto) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/status")
                .body(dto)
                .retrieve()
                .body(PedidoEntregaResponseDTO.class);
    }

    public Map confirmarRecebimento(Long id) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/confirmar")
                .retrieve()
                .body(Map.class);
    }

    public List<PedidoEntregaResponseDTO> listarPedidosEmEntrega() {

        return restClient.get()
                .uri(getBaseUrl() + "/em-entrega/lista")
                .retrieve()
                .body(List.class);
    }

    public Map obterEstatisticasPedidosEmEntrega() {

        return restClient.get()
                .uri(getBaseUrl() + "/em-entrega/estatisticas")
                .retrieve()
                .body(Map.class);
    }

    public Map verificarSePedidoEmEntrega(Long id) {

        return restClient.get()
                .uri(getBaseUrl() + "/" + id + "/em-entrega")
                .retrieve()
                .body(Map.class);
    }

    public Map sincronizarCachePedidosEmEntrega() {

        return restClient.post()
                .uri(getBaseUrl() + "/em-entrega/sincronizar")
                .retrieve()
                .body(Map.class);
    }
}