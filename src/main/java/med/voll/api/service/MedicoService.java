package med.voll.api.service;

import med.voll.api.dto.medico.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.medico.DadosDetalhamentoMedicoDTO;
import med.voll.api.dto.medico.DadosMedicoDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.infrastructure.persistence.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public void cadastrar(Medico medico) {
        repository.save(medico);
    }

    public Page<DadosMedicoDTO> listarMedicos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable)
                .map(DadosMedicoDTO::new);
    }

    public DadosDetalhamentoMedicoDTO atualizarDados(DadosAtualizacaoMedicoDTO dadosAtualizados) {
        Optional<Medico> medico = repository.findById(dadosAtualizados.id());
        if (medico.isPresent()) {
            medico.get().atualizarInformacoes(dadosAtualizados);
            return new DadosDetalhamentoMedicoDTO(medico.get());
        }
        return null;
    }

    public boolean excluir(Long id) {
        Optional<Medico> medico = repository.findById(id);
        if (medico.isPresent()) {
            medico.get().excluir();
            return true;
        }
        return false;
    }

    public DadosDetalhamentoMedicoDTO detalharMedico(Long id) {
        Optional<Medico> medico = repository.findById(id);
        if (medico.isPresent()) {
            return new DadosDetalhamentoMedicoDTO(medico.get());
        }
        return null;
    }

}
