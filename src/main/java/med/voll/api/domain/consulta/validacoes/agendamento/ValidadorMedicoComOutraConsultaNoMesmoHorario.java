package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.infrastructure.persistence.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta  {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        Boolean doesMedicoTemConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (doesMedicoTemConsultaNoMesmoHorario) {
            throw new ValidacaoException("Medico ja possui outra consulta agendada nesse mesmo horario");
        }
    }

}
