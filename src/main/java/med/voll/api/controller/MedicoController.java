package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccionDTO;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedicoDTO> registrarMedico (@RequestBody @Valid DatosRegistroMedicoDTO datosRegistroMedicoDTO,
                                                                    UriComponentsBuilder uriComponentsBuilder){

        Medico medico = medicoRepository.save(new Medico(datosRegistroMedicoDTO));

        DatosRespuestaMedicoDTO datosRespuestaMedicoDTO = new DatosRespuestaMedicoDTO(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                new DatosDireccionDTO(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedicoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedicoDTO>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {

//        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicoDTO::new);

        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicoDTO::new));

    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedicoDTO> actualizarMedico(@RequestBody @Valid DatosActualizarMedicoDTO datosActualizarMedicoDTO) {

        System.out.println(datosActualizarMedicoDTO);
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedicoDTO.id());
        medico.actualizarDatos(datosActualizarMedicoDTO);

        return ResponseEntity.ok(new DatosRespuestaMedicoDTO(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                new DatosDireccionDTO(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Medico> eliminarMedico(@PathVariable Long id ){

        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);        //Hard-delete.
        medico.desactivarMedico();           //Soft-delete

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedicoDTO> retornarDatosMedico(@PathVariable Long id ){

        Medico medico = medicoRepository.getReferenceById(id);

        DatosRespuestaMedicoDTO datosRespuestaMedicoDTO = new DatosRespuestaMedicoDTO(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                new DatosDireccionDTO(
                        medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));

        return ResponseEntity.ok(datosRespuestaMedicoDTO);
    }
}
