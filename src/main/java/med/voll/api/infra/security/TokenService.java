package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.cdimascio.dotenv.Dotenv;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final Dotenv dotenv = Dotenv.load();

    public String generarToken(Usuario usuario) {

        String SECRET_KEY = dotenv.get("SECRET_KEY");
        int TOKEN_EXPIRE_TIME = Integer.parseInt(dotenv.get("TOKEN_EXPIRE_TIME"));
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion(TOKEN_EXPIRE_TIME))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    private Instant generarFechaExpiracion(int expirationHours) {
        return LocalDateTime.now().plusHours(expirationHours).toInstant(ZoneOffset.of("-06:00"));
    }

}
