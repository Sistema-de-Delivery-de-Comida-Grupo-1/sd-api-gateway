package br.ifg.urutai.sdapigateway.controller;

import br.ifg.urutai.sdapigateway.DTO.ListaTransacoesDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoCreteDTO;
import br.ifg.urutai.sdapigateway.DTO.PedidoResponseDTO;
import br.ifg.urutai.sdapigateway.DTO.SaldoDTO;
import br.ifg.urutai.sdapigateway.service.PedidoClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoClient pedidoClient;

    public PedidoController(PedidoClient pedidoClient) {
        this.pedidoClient = pedidoClient;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(
            @RequestBody PedidoCreteDTO dto) {

        return ResponseEntity.ok(
                pedidoClient.criarPedido(dto)
        );
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<PedidoResponseDTO> pagarPedido(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoClient.pagarPedido(id)
        );
    }

    @PutMapping("/{id}/estornar")
    public ResponseEntity<PedidoResponseDTO> estornarPedido(
            @PathVariable Long id,
            @RequestParam String motivo) {

        return ResponseEntity.ok(
                pedidoClient.estornarPedido(id, motivo)
        );
    }

    @PutMapping("/{id}/iniciar-preparo")
    public ResponseEntity<PedidoResponseDTO> iniciarPreparo(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoClient.iniciarPreparo(id)
        );
    }

    @PutMapping("/{id}/finalizar-preparo")
    public ResponseEntity<PedidoResponseDTO> finalizarPreparo(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoClient.finalizarPreparo(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PedidoCreteDTO dto) {

        return ResponseEntity.ok(
                pedidoClient.atualizar(id, dto)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoClient.buscarPorId(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(
                pedidoClient.listarTodos()
        );
    }

    @GetMapping("/pagamentos/saldo")
    public ResponseEntity<SaldoDTO> consultarSaldo() {

        return ResponseEntity.ok(
                pedidoClient.consultarSaldo()
        );
    }

    @GetMapping("/pagamentos/transacoes")
    public ResponseEntity<ListaTransacoesDTO> listarTransacoes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        return ResponseEntity.ok(
                pedidoClient.listarTransacoes(
                        pagina,
                        tamanho
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable Long id) {

        pedidoClient.remover(id);

        return ResponseEntity.noContent().build();
    }
}