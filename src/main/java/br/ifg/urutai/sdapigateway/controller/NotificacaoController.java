package br.ifg.urutai.sdapigateway.controller;


import br.ifg.urutai.sdapigateway.DTO.NotificationResponseDto;
import br.ifg.urutai.sdapigateway.DTO.PageResponse;
import br.ifg.urutai.sdapigateway.model.NotificationStatus;
import br.ifg.urutai.sdapigateway.service.NotificacaoClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificacaoController {

    private final NotificacaoClient notificacaoClient;

    public NotificacaoController(NotificacaoClient notificacaoClient) {
        this.notificacaoClient = notificacaoClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificacaoClient.buscarPorId(id)
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationResponseDto>> buscarPorPedido(
            @PathVariable String orderId) {

        return ResponseEntity.ok(
                notificacaoClient.buscarPorPedido(orderId)
        );
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<PageResponse<NotificationResponseDto>> buscarPorCliente(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                notificacaoClient.buscarPorCliente(
                        email,
                        page,
                        size
                )
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PageResponse<NotificationResponseDto>> buscarPorStatus(
            @PathVariable NotificationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                notificacaoClient.buscarPorStatus(
                        status,
                        page,
                        size
                )
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<NotificationResponseDto>> listarTodas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                notificacaoClient.listarTodas(
                        page,
                        size
                )
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<NotificationResponseDto>> historico(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return ResponseEntity.ok(
                notificacaoClient.historico(
                        startDate,
                        endDate
                )
        );
    }

    @GetMapping("/stats/pending")
    public ResponseEntity<Long> contarPendentes() {

        return ResponseEntity.ok(
                notificacaoClient.contarPendentes()
        );
    }

    @GetMapping("/stats/customer/{email}")
    public ResponseEntity<Long> contarEnviadasPorCliente(
            @PathVariable String email) {

        return ResponseEntity.ok(
                notificacaoClient.contarEnviadasPorCliente(email)
        );
    }

    @PutMapping("/{id}/mark-as-sent")
    public ResponseEntity<NotificationResponseDto> marcarComoEnviada(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificacaoClient.marcarComoEnviada(id)
        );
    }

    @PutMapping("/{id}/mark-as-failed")
    public ResponseEntity<NotificationResponseDto> marcarComoFalha(
            @PathVariable Long id,
            @RequestParam(defaultValue = "Erro desconhecido")
            String errorMessage) {

        return ResponseEntity.ok(
                notificacaoClient.marcarComoFalha(
                        id,
                        errorMessage
                )
        );
    }
}