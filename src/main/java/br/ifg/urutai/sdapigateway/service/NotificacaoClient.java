package br.ifg.urutai.sdapigateway.service;

import br.ifg.urutai.sdapigateway.DTO.NotificationResponseDto;
import br.ifg.urutai.sdapigateway.DTO.PageResponse;
import br.ifg.urutai.sdapigateway.model.NotificationStatus;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class NotificacaoClient {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public NotificacaoClient(
            RestClient.Builder builder,
            DiscoveryClient discoveryClient) {

        this.restClient = builder.build();
        this.discoveryClient = discoveryClient;
    }

    private String getBaseUrl() {

        List<ServiceInstance> instances =
                discoveryClient.getInstances("SD-NOTIFICACAO");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "MICROSSERVICO-NOTIFICACAO não encontrado no Eureka");
        }

        ServiceInstance instance = instances.get(0);

        return String.format(
                "http://%s:%d/api/v1/notifications",
                instance.getHost(),
                instance.getPort()
        );
    }

    public NotificationResponseDto buscarPorId(Long id) {

        return restClient.get()
                .uri(getBaseUrl() + "/" + id)
                .retrieve()
                .body(NotificationResponseDto.class);
    }

    public List<NotificationResponseDto> buscarPorPedido(String orderId) {

        return restClient.get()
                .uri(getBaseUrl() + "/order/" + orderId)
                .retrieve()
                .body(List.class);
    }

    public PageResponse<NotificationResponseDto> buscarPorCliente(
            String email,
            int page,
            int size) {

        return restClient.get()
                .uri(getBaseUrl()
                        + "/customer/" + email
                        + "?page=" + page
                        + "&size=" + size)
                .retrieve()
                .body(PageResponse.class);
    }

    public PageResponse<NotificationResponseDto> buscarPorStatus(
            NotificationStatus status,
            int page,
            int size) {

        return restClient.get()
                .uri(getBaseUrl()
                        + "/status/" + status
                        + "?page=" + page
                        + "&size=" + size)
                .retrieve()
                .body(PageResponse.class);
    }

    public PageResponse<NotificationResponseDto> listarTodas(
            int page,
            int size) {

        return restClient.get()
                .uri(getBaseUrl()
                        + "?page=" + page
                        + "&size=" + size)
                .retrieve()
                .body(PageResponse.class);
    }

    public List<NotificationResponseDto> historico(
            String startDate,
            String endDate) {

        return restClient.get()
                .uri(getBaseUrl()
                        + "/history"
                        + "?startDate=" + startDate
                        + "&endDate=" + endDate)
                .retrieve()
                .body(List.class);
    }

    public Long contarPendentes() {

        return restClient.get()
                .uri(getBaseUrl() + "/stats/pending")
                .retrieve()
                .body(Long.class);
    }

    public Long contarEnviadasPorCliente(String email) {

        return restClient.get()
                .uri(getBaseUrl() + "/stats/customer/" + email)
                .retrieve()
                .body(Long.class);
    }

    public NotificationResponseDto marcarComoEnviada(Long id) {

        return restClient.put()
                .uri(getBaseUrl() + "/" + id + "/mark-as-sent")
                .retrieve()
                .body(NotificationResponseDto.class);
    }

    public NotificationResponseDto marcarComoFalha(
            Long id,
            String errorMessage) {

        return restClient.put()
                .uri(getBaseUrl()
                        + "/" + id
                        + "/mark-as-failed"
                        + "?errorMessage=" + errorMessage)
                .retrieve()
                .body(NotificationResponseDto.class);
    }
}