package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsultaDTO;

public class MedicoConConsulta {

    private ConsultaRepository repository;

    public void validar(DatosAgendarConsultaDTO datos) {

        if (datos.idMedico() == null)
            return;

        var medicoConConsulta = repository.existMedicoIdAndFecha(datos.idMedico(), datos.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("Este médico no está disponible en el horario seleccionado.");
        }

    }
}
