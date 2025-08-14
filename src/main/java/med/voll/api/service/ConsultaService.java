package med.voll.api.service;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import med.voll.api.infrastructure.persistence.consulta.ConsultaRepository;
import med.voll.api.infrastructure.persistence.medico.MedicoRepository;
import med.voll.api.infrastructure.persistence.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado nao existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado nao existe!");
        }

        // 3 dos principios de solid foram aplicados na implementacao desses validadores e a forma como foram desenvolvidos
        // S O D
        validadores.forEach(v -> v.validar(dados));

        Medico medico = escolherMedico(dados);
        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        if (medico == null) {
            throw new ValidacaoException("Nao existe medico disponivel nessa data!");
        }

        Consulta consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado nao existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade e obrigatoria quando medico nao for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioDisponivelNaData(dados.especialidade(), dados.data());
    }


}
