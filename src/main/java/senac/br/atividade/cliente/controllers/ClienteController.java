package senac.br.atividade.cliente.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senac.br.atividade.cliente.controllers.dtos.ClienteRequestDTO;
import senac.br.atividade.cliente.modelos.Cliente;
import senac.br.atividade.cliente.repositorios.ClienteRepositorio;
import senac.br.atividade.cliente.services.ClienteService;

import java.util.List;

@Controller
@RequestMapping("/cliente")
@CrossOrigin
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar")
    public ResponseEntity<Void> cadastrarCliente(@RequestBody ClienteRequestDTO cliente) {
        clienteService.criarCliente(cliente);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes(){return ResponseEntity.ok(clienteService.listarClientes());}
}
