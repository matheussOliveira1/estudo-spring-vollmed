package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.dto.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);

}
