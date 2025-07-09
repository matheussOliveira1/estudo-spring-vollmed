package med.voll.api.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.dto.medico.DadosMedicoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MedicoException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream()
                .map(DadosErroValidacaoDTO::new).toList());
    }

    private record DadosErroValidacaoDTO(
            String campo,
            String mensagem
    )
    {

        public DadosErroValidacaoDTO(FieldError err){
            this(err.getField(), err.getDefaultMessage());
        }

    }

}
