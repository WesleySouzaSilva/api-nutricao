package br.com.nutricao.infrastructure.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutricao.domain.model.RegistroDiario;

public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Integer> {
    Optional<RegistroDiario> findByUsuarioIdAndData(Integer usuarioId, LocalDate data);
    List<RegistroDiario> findByUsuarioIdOrderByDataDesc(Integer usuarioId);
    List<RegistroDiario> findByUsuarioIdAndDataBetweenOrderByDataDesc(Integer usuarioId, LocalDate inicio, LocalDate fim);
}
