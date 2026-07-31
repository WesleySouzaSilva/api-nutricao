package br.com.nutricao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.nutricao.domain.RegistroDiario;

public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Integer>, JpaSpecificationExecutor<RegistroDiario> {
    Optional<RegistroDiario> findByUsuarioIdAndData(Integer usuarioId, LocalDate data);
    List<RegistroDiario> findByUsuarioIdOrderByDataDesc(Integer usuarioId);
    List<RegistroDiario> findByUsuarioIdAndDataBetweenOrderByDataDesc(Integer usuarioId, LocalDate inicio, LocalDate fim);
}
