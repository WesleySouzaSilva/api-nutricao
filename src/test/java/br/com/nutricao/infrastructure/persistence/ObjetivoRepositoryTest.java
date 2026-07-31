package br.com.nutricao.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.model.Objetivo;
import br.com.nutricao.domain.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class ObjetivoRepositoryTest {

    @Autowired
    private ObjetivoRepository objetivoRepository;

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
        Objetivo objetivo = new Objetivo();
        objetivo.setUsuario(usuario);
        objetivo.setTipo("EMAGRECER");
        objetivo.setDataInicio(LocalDate.now());
        Objetivo saved = objetivoRepository.save(objetivo);
        assertNotNull(saved.getId());
        assertEquals("EMAGRECER", saved.getTipo());
    }

    @Test
    void findByUsuarioId_DeveRetornarObjetivos() {
        Objetivo objetivo = new Objetivo();
        objetivo.setUsuario(usuario);
        objetivo.setTipo("HIPERTROFIA");
        objetivo.setDataInicio(LocalDate.now());
        objetivoRepository.save(objetivo);

        List<Objetivo> result = objetivoRepository.findByUsuarioId(usuario.getId());
        assertEquals(1, result.size());
    }
}
