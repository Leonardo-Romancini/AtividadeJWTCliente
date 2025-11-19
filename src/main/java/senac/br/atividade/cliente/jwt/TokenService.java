package senac.br.atividade.cliente.jwt;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import senac.br.atividade.cliente.modelos.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private String secret = "seguranca_api";
    private String withIssuer = "ativididade-cliente-api";


    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String tokenJwt = JWT.create().withIssuer(withIssuer)
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(this.gerarDataValidaToken())
                    .sign(algorithm);

            return tokenJwt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer(withIssuer)
                    .build().verify(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private Instant gerarDataValidaToken() {
        return LocalDateTime
                .now()
                .plusHours(1)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
