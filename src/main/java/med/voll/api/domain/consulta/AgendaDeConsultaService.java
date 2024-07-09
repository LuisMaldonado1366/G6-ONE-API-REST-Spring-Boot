package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsultaDTO agendar(DatosAgendarConsultaDTO datos){

        if(pacienteRepository.findById(datos.idPaciente()).isEmpty()){
            throw new ValidacionDeIntegridad("No se permiten citas a pacientes inactivos.");
        }

        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("No se permiten citas con medicos inactivos.");
        }

        validadores.forEach(v -> v.validar(datos));
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad.");
        }

        var consulta = new Consulta(null,medico,paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsultaDTO(consulta);

    }

    public void cancelar(DatosDetalleConsultaDTO datos) {
        if(!consultaRepository.existsById(datos.id())) {
            throw new ValidacionDeIntegridad("Id de consulta erroneo.");

        }
    }

    private Medico seleccionarMedico(DatosAgendarConsultaDTO datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }


        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}