package med.voll.api.dto.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dto.endereco.DadosEnderecoDTO;
import med.voll.api.model.medico.Especialidade;

public record DadosAtualizacaoMedicoDTO(
        @NotNull Long id,
        String nome,
        String telefone,
        DadosEnderecoDTO enderecoDTO
) {
}
