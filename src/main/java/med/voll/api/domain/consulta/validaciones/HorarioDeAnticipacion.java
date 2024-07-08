package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsultaDTO;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class HorarioDeAnticipacion {

    public void validar(DatosAgendarConsultaDTO datos) {

        var ahora = LocalDateTime.now();
        var horaCita = datos.fecha();
        var diferenciaDeTreintaMinutos = Duration.between(ahora, horaCita).toMinutes()<30;
        if (diferenciaDeTreintaMinutos) {
            throw new ValidationException("La consulta debe ser agendada con al menos 30 minutos de anticipación.");
        }
    }

}
