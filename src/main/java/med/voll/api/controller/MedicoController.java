package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.medico.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.medico.DadosCadastroMedicoDTO;
import med.voll.api.dto.medico.DadosMedicoDTO;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados) {
        service.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosMedicoDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return service.listarMedicos(pageable);
    }

    @PutMapping
    @Transactional
    public void atualizarDados(@RequestBody @Valid DadosAtualizacaoMedicoDTO dadosAtualizados){
        service.atualizarDados(dadosAtualizados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

}
