package senac.br.atividade.cliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senac.br.atividade.cliente.controllers.dtos.ClienteRequestDTO;
import senac.br.atividade.cliente.modelos.Cliente;
import senac.br.atividade.cliente.repositorios.ClienteRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public void criarCliente(ClienteRequestDTO cliente) {
        Optional<Cliente> clienteResult = clienteRepositorio.findByDocumento(cliente.getDocumento().replace("-","")
                .replace(".",""));
        if(clienteResult.isPresent()) {
            throw new RuntimeException("Cliente já cadastrado.");
        }
        Cliente clientePersist = new Cliente();
        clientePersist.setDocumento(cliente.getDocumento().replace("-","").replace(".",""));
        clientePersist.setDataNascimento(cliente.getDataNascimento());
        clientePersist.setEmail(cliente.getEmail());
        clientePersist.setNome(cliente.getNome());
        clientePersist.setSobrenome(cliente.getSobrenome());

        clienteRepositorio.save(clientePersist);
    }

    public List<Cliente> listarClientes(){
        return clienteRepositorio.findAll();
    }
}
