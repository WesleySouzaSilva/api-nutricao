package br.com.nutricao.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.model.MetaNutricional;
import br.com.nutricao.domain.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class MetaNutricionalRepositoryTest {

    @Autowired
    private MetaNutricionalRepository metaNutricionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNome("Joao");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("123");
        usuario.setSexo("MASCULINO");
        usuario.setMedida("KG");
        usuario.setTipoLogin("EMAIL");
        usuario.setDataCadastro(LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void save_ComDadosValidos_DevePersistir() {
        MetaNutricional meta = new MetaNutricional();
        meta.setUsuario(usuario);
        meta.setCalorias(new BigDecimal("2500"));
        meta.setProteinas(new BigDecimal("150"));
        meta.setDataInicio(LocalDate.now());
        MetaNutricional saved = metaNutricionalRepository.save(meta);
        assertNotNull(saved.getId());
        assertEquals(0, new BigDecimal("2500").compareTo(saved.getCalorias()));
    }

    @Test
    void findByUsuarioId_DeveRetornarMetas() {
        MetaNutricional meta = new MetaNutricional();
        meta.setUsuario(usuario);
        meta.setCalorias(new BigDecimal("2500"));
        meta.setDataInicio(LocalDate.now());
        metaNutricionalRepository.save(meta);

        List<MetaNutricional> result = metaNutricionalRepository.findByUsuarioId(usuario.getId());
        assertEquals(1, result.size());
    }

    @Test
    void findFirstByUsuarioIdOrderByDataInicioDesc_DeveRetornarUltimaMeta() {
        MetaNutricional m1 = new MetaNutricional();
        m1.setUsuario(usuario);
        m1.setCalorias(new BigDecimal("2000"));
        m1.setDataInicio(LocalDate.of(2024, 1, 1));
        metaNutricionalRepository.save(m1);

        MetaNutricional m2 = new MetaNutricional();
        m2.setUsuario(usuario);
        m2.setCalorias(new BigDecimal("2500"));
        m2.setDataInicio(LocalDate.of(2024, 6, 1));
        metaNutricionalRepository.save(m2);

        Optional<MetaNutricional> last = metaNutricionalRepository.findFirstByUsuarioIdOrderByDataInicioDesc(usuario.getId());
        assertTrue(last.isPresent());
        assertEquals(0, new BigDecimal("2500").compareTo(last.get().getCalorias()));
    }
}
