package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.infrastructure.persistence.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCancelamentoConsulta dados) {

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        LocalDateTime agora = LocalDateTime.now();
        Long diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedencia minima de 24h!");
        }

    }

}
