package br.ifg.urutai.sdapigateway.controller;

import br.ifg.urutai.sdapigateway.DTO.LoginDTO;
import br.ifg.urutai.sdapigateway.DTO.UsuarioCadastroDTO;
import br.ifg.urutai.sdapigateway.DTO.UsuarioResumoDTO;
import br.ifg.urutai.sdapigateway.model.Usuario;
import br.ifg.urutai.sdapigateway.service.UsuarioClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioClient usuarioClient;

    public UsuarioController(UsuarioClient usuarioClient) {
        this.usuarioClient = usuarioClient;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioCadastroDTO dto) {

        Usuario usuario = usuarioClient.cadastrar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(usuarioClient.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {

        return ResponseEntity.ok(usuarioClient.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioCadastroDTO dto) {

        return ResponseEntity.ok(usuarioClient.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {

        usuarioClient.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> realizarLogin(@RequestBody LoginDTO dto) {

        return ResponseEntity.ok(usuarioClient.realizarLogin(dto));
    }

    @GetMapping("/{id}/resumo")
    public ResponseEntity<UsuarioResumoDTO> buscarResumo(@PathVariable Long id) {

        return ResponseEntity.ok(usuarioClient.buscarResumo(id));
    }
}