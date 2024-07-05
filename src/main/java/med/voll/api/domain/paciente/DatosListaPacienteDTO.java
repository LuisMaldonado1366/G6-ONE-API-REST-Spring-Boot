package med.voll.api.domain.paciente;

public record DatosListaPacienteDTO(Long id, String nombre, String email, String documento) {

    public DatosListaPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }

}
