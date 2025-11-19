package senac.br.atividade.cliente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import senac.br.atividade.cliente.controllers.dtos.UsuarioRequestDTO;
import senac.br.atividade.cliente.services.UsuarioService;
import senac.br.atividade.cliente.utils.ResponseUtil;

@Controller
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastroUsuario(@RequestBody UsuarioRequestDTO usuario) {
        try {
            return ResponseEntity.created(null)
                    .body(usuarioService.cadastrarUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.response(e.getMessage()));
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequestDTO usuario) {
        try {
            return ResponseEntity.ok(usuarioService.loginUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseUtil.response(e.getMessage()));
        }
    }
}
