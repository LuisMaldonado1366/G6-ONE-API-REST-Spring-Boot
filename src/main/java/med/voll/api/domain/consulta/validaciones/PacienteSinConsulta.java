package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsultaDTO;

public class PacienteSinConsulta {

    private ConsultaRepository repository;

    public void validar(DatosAgendarConsultaDTO datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHirario = datos.fecha().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHirario);

        if (pacienteConConsulta) {
            throw new ValidationException("El paciente ya cuenta con una cita programada para este día.");
        }
    }
}
