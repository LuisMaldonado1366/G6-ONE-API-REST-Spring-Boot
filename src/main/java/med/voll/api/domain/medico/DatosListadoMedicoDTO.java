package med.voll.api.domain.medico;

public record DatosListadoMedicoDTO (
        Long id,
        String nombre,
        String document,
        String especialidad,
        String email
){
    public DatosListadoMedicoDTO(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getDocumento(), medico.getEspecialidad().toString(), medico.getEmail());
    }
}
