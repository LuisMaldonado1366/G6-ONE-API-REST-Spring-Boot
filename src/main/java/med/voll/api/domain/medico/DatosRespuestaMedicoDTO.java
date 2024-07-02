package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccionDTO;

public record DatosRespuestaMedicoDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccionDTO direccion
) {
}
