package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.infrastructure.persistence.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta  {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }

        Boolean isMedicoAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if (!isMedicoAtivo) {
            throw new ValidacaoException("Consulta nao pode ser agendada com medico excluido/desativado no sistema!");
        }
    }

}
