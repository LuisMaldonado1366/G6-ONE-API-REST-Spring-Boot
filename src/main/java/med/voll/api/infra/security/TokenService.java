package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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
    private final String SECRET_KEY = dotenv.get("SECRET_KEY");
    private final String TOKEN_ISSUER = dotenv.get("TOKEN_ISSUER");

    public String generarToken(Usuario usuario) {

        int TOKEN_EXPIRE_TIME = Integer.parseInt(dotenv.get("TOKEN_EXPIRE_TIME"));
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer(TOKEN_ISSUER)
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

    public String getSubjec(String token) {
        if (token == null) {
            throw new RuntimeException();
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer(TOKEN_ISSUER)
                    .build()
                    .verify(token);
            return verifier.getSubject();
        } catch (JWTVerificationException | NullPointerException exception){
            return exception.getMessage();
        }

    }

}
