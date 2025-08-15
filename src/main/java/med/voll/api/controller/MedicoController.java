package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.dto.medico.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.medico.DadosCadastroMedicoDTO;
import med.voll.api.dto.medico.DadosDetalhamentoMedicoDTO;
import med.voll.api.dto.medico.DadosMedicoDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicoDTO dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        service.cadastrar(medico);
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicoDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosMedicoDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<DadosMedicoDTO> medicos = service.listarMedicos(pageable);
        if(!medicos.isEmpty()) {
            return ResponseEntity.ok(medicos);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarDados(@RequestBody @Valid DadosAtualizacaoMedicoDTO dadosAtualizados){
        DadosDetalhamentoMedicoDTO dadosAtualizadosMedico = service.atualizarDados(dadosAtualizados);
        if (dadosAtualizadosMedico != null) {
            return ResponseEntity.ok(dadosAtualizados);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        if (service.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DadosDetalhamentoMedicoDTO> detalharDados(@PathVariable Long id) {
        DadosDetalhamentoMedicoDTO medico = service.detalharMedico(id);
        if (medico != null) {
            return ResponseEntity.ok(medico);
        }
        return ResponseEntity.notFound().build();
    }

}
