package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccionDTO;

public record DatosActualizarMedicoDTO(
        @NotNull
        Long id,
        String nombre,
        String documento,
        DatosDireccionDTO direccion
) {
}
