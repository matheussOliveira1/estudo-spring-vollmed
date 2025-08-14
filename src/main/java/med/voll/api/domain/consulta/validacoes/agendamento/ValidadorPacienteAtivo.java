package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.infrastructure.persistence.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta  {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        Boolean isPacienteAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

        if (!isPacienteAtivo) {
            throw new ValidacaoException("Consulta nao pode ser agendada com paciente excluido/desativado no sistema!");
        }
    }


}
