package com.toma.monitor_estudos.repository;

import com.toma.monitor_estudos.domain.SessaoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessaoEstudoRepository extends JpaRepository<SessaoEstudo, Long> {

    List<SessaoEstudo> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    Optional<SessaoEstudo> findByDataFimIsNull();
}
