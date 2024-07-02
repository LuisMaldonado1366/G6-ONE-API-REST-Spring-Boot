package med.voll.api.medico;

import med.voll.api.direccion.DatosDireccionDTO;

public record DatosRespuestaMedicoDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccionDTO direccion
) {
}
