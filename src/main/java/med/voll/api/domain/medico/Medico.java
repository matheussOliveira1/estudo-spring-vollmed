package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.common.Endereco;
import med.voll.api.dto.medico.DadosAtualizacaoMedicoDTO;
import med.voll.api.dto.medico.DadosCadastroMedicoDTO;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedicoDTO dadosMedico) {
        this.ativo = true;
        this.nome = dadosMedico.nome();
        this.email = dadosMedico.email();
        this.telefone = dadosMedico.telefone();
        this.crm = dadosMedico.crm();
        this.especialidade = dadosMedico.especialidade();
        this.endereco = new Endereco(dadosMedico.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedicoDTO dadosAtualizado){
        if (dadosAtualizado.nome() != null) {
            this.nome = dadosAtualizado.nome();
        }
        if (dadosAtualizado.telefone() != null) {
            this.telefone = dadosAtualizado.telefone();
        }
        if (dadosAtualizado.enderecoDTO() != null) {
            this.endereco.atualizarInformacoes(dadosAtualizado.enderecoDTO());
        }
    }

    public void excluir() {
        this.ativo = false;
    }

}
