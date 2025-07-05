package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.medico.DadosCadastroMedico;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        service.cadastrar(dados);
    }

//   @GetMapping
//   public List<Medico> listarMedicos() {
//
//   }

}
