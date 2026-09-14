package com.example.backhelp.repository;

import com.example.backhelp.model.ChamadoModel;
import com.example.backhelp.model.Perfil;
import com.example.backhelp.model.StatusChamado;
import com.example.backhelp.model.Urgencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoModel, Long> {

    Optional<ChamadoModel> findByProtocolo(String protocolo);

    List<ChamadoModel> findByUsuarioAberturaId(Long usuarioId);

    List<ChamadoModel> findByAtendenteResponsavelId(Long atendenteId);

    List<ChamadoModel> findByStatus(StatusChamado status);

    List<ChamadoModel> findByNivelAtendimento(Perfil nivelAtendimento);

    long countByStatus(StatusChamado status);

    long countByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT c FROM ChamadoModel c WHERE " +
            "(:status IS NULL OR c.status = :status) AND " +
            "(:nivel IS NULL OR c.nivelAtendimento = :nivel) AND " +
            "(:urgencia IS NULL OR c.urgencia = :urgencia)")
    List<ChamadoModel> buscarComFiltros(@Param("status") StatusChamado status,
                                        @Param("nivel") Perfil nivel,
                                        @Param("urgencia") Urgencia urgencia);

    @Query("SELECT COUNT(c) FROM ChamadoModel c WHERE c.status <> com.example.backhelp.model.StatusChamado.FECHADO AND c.dataLimiteSla < :agora")
    long countAtrasados(@Param("agora") LocalDateTime agora);

    @Query("SELECT COUNT(c) FROM ChamadoModel c WHERE c.status = com.example.backhelp.model.StatusChamado.ABERTO AND (c.dataLimiteSla >= :agora OR c.dataLimiteSla IS NULL)")
    long countAbertosNaoAtrasados(@Param("agora") LocalDateTime agora);
}