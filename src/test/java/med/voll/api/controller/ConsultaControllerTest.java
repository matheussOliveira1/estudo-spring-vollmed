package med.voll.api.controller;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.DadosAgendamentoConsulta;
import med.voll.api.dto.consulta.DadosDetalhamentoConsulta;
import med.voll.api.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc // -> Obrigatorio para utilizar o MockMvc com o spring, serve para a configuracao ser feita automaticamente
@AutoConfigureJsonTesters // -> obrigatorio para utilizar jackson tester
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc; // -> Classe para mockar requisicoes, autowired e necessario para que a classe seja injetada automaticamente

    //JacksonTester -> Classe de apoio para auxiliar no corpo de uma requisicao
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;
    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    @MockitoBean
    private ConsultaService agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser() // -> Caso as requisicoes tenham autenticacao, essa anotacao serve para que nos utilizemos um usuario mockado
    void agendarCenario1() throws Exception {
        //Forma de testar uma controller unitariamente, fingindo uma requisicao
        var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser() // -> Caso as requisicoes tenham autenticacao, essa anotacao serve para que nos utilizemos um usuario mockado
    void agendarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialiadade = Especialidade.CARDIOLOGIA;

        // Classe retornada mockada para utilizar na validacao do teste e na utilizacao da classe utilitaria do mockito
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 1l, data);

        // Metodo utilitario do mockito para mockar um processamento utilizando classes e uma resposta ja esperada
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        //Forma de testar uma controller unitariamente, fingindo uma requisicao
        //Com o jackson tester, podemos enviar um objeto java em forma de json para ao inves de precisarmos
            //escrever um json, utilizarmos uma classe java para mockar os dados obrigatorios de um corpo de requisicao
        var response = mockMvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJacksonTester.write(
                                new DadosAgendamentoConsulta(2l, 1l, data, especialiadade)
                        ).getJson()) // -> Obrigatorio utilizar este .getJson para a classe java ser transformada em json
        ).andReturn().getResponse();

        // Primeira validacao para verificar se o status veio 200
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhamentoConsultaJacksonTester.write(dadosDetalhamento).getJson();

        // Segunda validacao para verificar se o resultado (corpo devolvido) foi o mesmo que o esperado
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}