package med.voll.api.infrastructure.persistence.medico;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            SELECT m FROM Medico m WHERE m.ativo = TRUE AND m.especialidade = :especialidade 
            AND m.id NOT IN( SELECT c.medico.id FROM Consulta c WHERE c.data = :data AND c.motivoCancelamento IS NULL) 
            ORDER BY RAND() LIMIT 1
            """)
    Medico escolherMedicoAleatorioDisponivelNaData(Especialidade especialidade, LocalDateTime data);

    @Query("SELECT m.ativo FROM Medico m WHERE m.id = :idMedico")
    Boolean findAtivoById(Long idMedico);
}
