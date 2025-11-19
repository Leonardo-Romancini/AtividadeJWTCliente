package senac.br.atividade.cliente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import senac.br.atividade.cliente.controllers.dtos.UsuarioRequestDTO;
import senac.br.atividade.cliente.controllers.dtos.UsuarioResponseDTO;
import senac.br.atividade.cliente.jwt.TokenService;
import senac.br.atividade.cliente.modelos.Usuario;
import senac.br.atividade.cliente.repositorios.UsuarioRepositorio;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuario) {
        Optional<Usuario> usuarioResult = usuarioRepositorio.findByEmail(usuario.getEmail());
        if(usuarioResult.isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Usuario usuarioPersist = new Usuario();
        usuarioPersist.setEmail(usuario.getEmail());
        usuarioPersist.setNome(usuario.getNome());
        usuarioPersist.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioFinal = usuarioRepositorio.save(usuarioPersist);

        UsuarioResponseDTO retorno = new UsuarioResponseDTO();
        retorno.setEmail(usuarioFinal.getEmail());
        retorno.setNome(usuarioFinal.getNome());
        retorno.setToken(tokenService.gerarToken(usuarioFinal));

        return retorno;
    }

    public UsuarioResponseDTO loginUsuario(UsuarioRequestDTO usuario) {
        Optional<Usuario> resultUsuario = usuarioRepositorio.findByEmail(usuario.getEmail());
        if(resultUsuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Usuario usuarioLogin = resultUsuario.get();

        if(passwordEncoder.matches(usuario.getSenha(), usuarioLogin.getSenha())) {
            UsuarioResponseDTO response = new UsuarioResponseDTO();
            response.setEmail((usuarioLogin.getEmail()));
            response.setNome(usuarioLogin.getNome());
            response.setToken(tokenService.gerarToken(usuarioLogin));

            return response;
        }

        throw new RuntimeException("Senha inválida.");
    }
}
