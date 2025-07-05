package med.voll.api.dto.paciente;

import med.voll.api.model.paciente.Paciente;

public record DadosListagemPacienteDTO(Long id, String nome, String email, String cpf) {

    public DadosListagemPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }

}
