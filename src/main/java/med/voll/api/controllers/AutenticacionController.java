package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity autenticarUsuario (@RequestBody @Valid DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) {
        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        System.out.println(datosAutenticacionUsuario);
        authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
