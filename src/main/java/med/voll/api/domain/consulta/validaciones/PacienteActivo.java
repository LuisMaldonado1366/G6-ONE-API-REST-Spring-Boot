package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsultaDTO;
import med.voll.api.domain.paciente.PacienteRepository;

public class PacienteActivo {

    private PacienteRepository repository;

    public void validar(DatosAgendarConsultaDTO datos) {

        if (datos.idPaciente() == null) {
            return;
        }

        var pacienteActivo = repository.findActivoById(datos.idPaciente());

        if (!pacienteActivo) {
            throw new ValidationException("No se permite agendar citas a pacientes inactivos en el sistema.");
        }
    }
}
