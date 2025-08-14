package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta  {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        Boolean isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        Boolean isAntesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        Boolean isDepoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        if (isDomingo || isAntesDaAberturaDaClinica || isDepoisDoEncerramentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }
    }

}
