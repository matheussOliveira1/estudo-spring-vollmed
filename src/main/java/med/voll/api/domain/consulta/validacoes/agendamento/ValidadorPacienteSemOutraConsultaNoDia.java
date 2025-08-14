package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.infrastructure.persistence.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta  {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        LocalDateTime primeiroHorario = dados.data().withHour(7);
        LocalDateTime ultimoHorario = dados.data().withHour(18);
        Boolean isPacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (isPacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente ja possui uma consulta agendada nesse dia");
        }
    }

}
