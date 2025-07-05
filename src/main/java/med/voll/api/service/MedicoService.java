package med.voll.api.service;

import med.voll.api.dto.medico.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.medico.DadosCadastroMedicoDTO;
import med.voll.api.dto.medico.DadosMedicoDTO;
import med.voll.api.model.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public void cadastrar(DadosCadastroMedicoDTO dados) {
        repository.save(new Medico(dados));
    }

    public Page<DadosMedicoDTO> listarMedicos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(DadosMedicoDTO::new);
    }

    public void atualizarDados(DadosAtualizacaoMedicoDTO dadosAtualizados) {
        Optional<Medico> medico = repository.findById(dadosAtualizados.id());
        if (medico.isPresent()) {
            medico.get().atualizarInformacoes(dadosAtualizados);
        } else {
            System.out.println("Medico nao encontrado");
        }
    }

}
