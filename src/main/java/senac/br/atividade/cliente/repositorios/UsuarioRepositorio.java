package senac.br.atividade.cliente.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import senac.br.atividade.cliente.modelos.Cliente;
import senac.br.atividade.cliente.modelos.Usuario;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
