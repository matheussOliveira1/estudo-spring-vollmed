package med.voll.api.service;

import med.voll.api.model.endereco.Endereco;
import med.voll.api.model.medico.DadosCadastroMedico;
import med.voll.api.model.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public void cadastrar(DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

}
