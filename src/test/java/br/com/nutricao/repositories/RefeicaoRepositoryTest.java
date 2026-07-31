package br.com.nutricao.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.Refeicao;
import br.com.nutricao.domain.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class RefeicaoRepositoryTest {

    @Autowired
    private RefeicaoRepository refeicaoRepository;

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
        Refeicao refeicao = new Refeicao();
        refeicao.setNome("Cafe da manha");
        refeicao.setDataRefeicao(LocalDateTime.now());
        refeicao.setUsuario(usuario);
        Refeicao saved = refeicaoRepository.save(refeicao);
        assertNotNull(saved.getId());
        assertEquals("Cafe da manha", saved.getNome());
    }

    @Test
    void findByUsuarioId_DeveRetornarRefeicoesDoUsuario() {
        refeicaoRepository.save(criarRefeicao("Cafe da manha"));
        refeicaoRepository.save(criarRefeicao("Almoco"));
        List<Refeicao> result = refeicaoRepository.findByUsuarioIdOrderByDataRefeicaoDesc(usuario.getId());
        assertEquals(2, result.size());
    }

    private Refeicao criarRefeicao(String nome) {
        Refeicao refeicao = new Refeicao();
        refeicao.setNome(nome);
        refeicao.setDataRefeicao(LocalDateTime.now());
        refeicao.setUsuario(usuario);
        return refeicao;
    }
}
