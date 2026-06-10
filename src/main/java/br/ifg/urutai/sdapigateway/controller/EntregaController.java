package br.ifg.urutai.sdapigateway.controller;

import br.ifg.urutai.sdapigateway.DTO.AtualizarStatusDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoEntregaRequestDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoEntregaResponseDTO;
import br.ifg.urutai.sdapigateway.service.EntregaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaClient entregaClient;

    public EntregaController(EntregaClient entregaClient) {
        this.entregaClient = entregaClient;
    }

    @PostMapping
    public ResponseEntity<PedidoEntregaResponseDTO> criarPedido(
            @RequestBody PedidoEntregaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(entregaClient.criarPedido(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntregaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                entregaClient.buscarPorId(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<PedidoEntregaResponseDTO>> listarTodos() {

        return ResponseEntity.ok(
                entregaClient.listarTodos()
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoEntregaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusDTO dto) {

        return ResponseEntity.ok(
                entregaClient.atualizarStatus(id, dto)
        );
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Map> confirmarRecebimento(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                entregaClient.confirmarRecebimento(id)
        );
    }

    @GetMapping("/em-entrega/lista")
    public ResponseEntity<List<PedidoEntregaResponseDTO>> listarPedidosEmEntrega() {

        return ResponseEntity.ok(
                entregaClient.listarPedidosEmEntrega()
        );
    }

    @GetMapping("/em-entrega/estatisticas")
    public ResponseEntity<Map> obterEstatisticasPedidosEmEntrega() {

        return ResponseEntity.ok(
                entregaClient.obterEstatisticasPedidosEmEntrega()
        );
    }

    @GetMapping("/{id}/em-entrega")
    public ResponseEntity<Map> verificarSePedidoEmEntrega(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                entregaClient.verificarSePedidoEmEntrega(id)
        );
    }

    @PostMapping("/em-entrega/sincronizar")
    public ResponseEntity<Map> sincronizarCachePedidosEmEntrega() {

        return ResponseEntity.ok(
                entregaClient.sincronizarCachePedidosEmEntrega()
        );
    }
}