package br.com.nutricao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.nutricao.domain.MetaNutricional;

public interface MetaNutricionalRepository extends JpaRepository<MetaNutricional, Integer>, JpaSpecificationExecutor<MetaNutricional> {
    List<MetaNutricional> findByUsuarioId(Integer usuarioId);
    Optional<MetaNutricional> findFirstByUsuarioIdOrderByDataInicioDesc(Integer usuarioId);
}
