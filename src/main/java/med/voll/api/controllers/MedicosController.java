package med.voll.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public String registrarMedico (@RequestBody @Valid DatosRegistroMedicoDTO datosRegistroMedicoDTO){

        Medico medico = new Medico(datosRegistroMedicoDTO);
        medicoRepository.save(medico);

        return "medico registrado";
    }

    @GetMapping
    public Page<DatosListadoMedicoDTO> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedicoDTO::new);
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicoDTO::new);

    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedicoDTO datosActualizarMedicoDTO) {

        System.out.println(datosActualizarMedicoDTO);
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedicoDTO.id());
        medico.actualizarDatos(datosActualizarMedicoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String eliminarMedico(@PathVariable Long id ){

        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);        //Hard-delete.
        medico.desactivarMedico();           //Soft-delete
        return "medico borrado " + id;
    }
}
