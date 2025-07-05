package med.voll.api.service;

import med.voll.api.dto.paciente.DadosAtualizacaoPacienteDTO;
import med.voll.api.dto.paciente.DadosCadastroPacienteDTO;
import med.voll.api.dto.paciente.DadosListagemPacienteDTO;
import med.voll.api.model.paciente.Paciente;
import med.voll.api.repository.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository repository;

    public void cadastrar(DadosCadastroPacienteDTO dados) {
        repository.save(new Paciente(dados));
    }

    public Page<DadosListagemPacienteDTO> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacienteDTO::new);
    }

    public void atualizar(DadosAtualizacaoPacienteDTO dados) {
        Optional<Paciente> paciente = repository.findById(dados.id());
        if (paciente.isPresent()){
            paciente.get().atualizarInformacoes(dados);
        }else {
            System.out.println("Paciente nao encontrado!");
        }
    }

    public void excluir(@PathVariable Long id) {
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()){
            paciente.get().excluir();
        }else {
            System.out.println("Paciente nao encontrado!");
        }
    }
}
