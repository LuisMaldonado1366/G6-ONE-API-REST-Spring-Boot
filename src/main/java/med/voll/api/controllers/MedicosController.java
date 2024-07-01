package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.medico.DatosListadoMedicoDTO;
import med.voll.api.medico.DatosRegistroMedicoDTO;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

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
        return medicoRepository.findAll(paginacion).map(DatosListadoMedicoDTO::new);

    }

    @PutMapping("/{id}")
    public void actualizarMedico(@PathVariable Long id) {


    }
}
