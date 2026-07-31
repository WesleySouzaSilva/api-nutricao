package br.com.nutricao.repositories;

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

import br.com.nutricao.domain.RegistroDiario;
import br.com.nutricao.domain.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class RegistroDiarioRepositoryTest {

    @Autowired
    private RegistroDiarioRepository registroDiarioRepository;

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
        RegistroDiario registro = new RegistroDiario();
        registro.setUsuario(usuario);
        registro.setData(LocalDate.now());
        registro.setCaloriasConsumidas(new BigDecimal("2000"));
        RegistroDiario saved = registroDiarioRepository.save(registro);
        assertNotNull(saved.getId());
        assertEquals(0, new BigDecimal("2000").compareTo(saved.getCaloriasConsumidas()));
    }

    @Test
    void findByUsuarioIdAndData_DeveRetornarRegistro() {
        RegistroDiario registro = new RegistroDiario();
        registro.setUsuario(usuario);
        registro.setData(LocalDate.now());
        registroDiarioRepository.save(registro);

        Optional<RegistroDiario> found = registroDiarioRepository.findByUsuarioIdAndData(usuario.getId(), LocalDate.now());
        assertTrue(found.isPresent());
    }

    @Test
    void findByUsuarioIdOrderByDataDesc_DeveRetornarOrdenado() {
        RegistroDiario r1 = new RegistroDiario();
        r1.setUsuario(usuario);
        r1.setData(LocalDate.of(2024, 1, 1));
        registroDiarioRepository.save(r1);

        RegistroDiario r2 = new RegistroDiario();
        r2.setUsuario(usuario);
        r2.setData(LocalDate.of(2024, 1, 2));
        registroDiarioRepository.save(r2);

        List<RegistroDiario> result = registroDiarioRepository.findByUsuarioIdOrderByDataDesc(usuario.getId());
        assertEquals(2, result.size());
        assertTrue(result.get(0).getData().isAfter(result.get(1).getData()));
    }
}
