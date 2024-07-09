package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsultaDTO;

public interface ValidadorDeConsultas {

    void validar(DatosAgendarConsultaDTO datos);
}
