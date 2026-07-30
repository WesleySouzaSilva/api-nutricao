package br.com.nutricao.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.AlimentoRefeicao;
import br.com.nutricao.domain.model.CategoriaAlimento;
import br.com.nutricao.domain.model.Refeicao;
import br.com.nutricao.domain.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class AlimentoRefeicaoRepositoryTest {

    @Autowired
    private AlimentoRefeicaoRepository alimentoRefeicaoRepository;

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private CategoriaAlimentoRepository categoriaAlimentoRepository;

    private Refeicao refeicao;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setNome("Joao");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("123");
        usuario.setSexo("MASCULINO");
        usuario.setMedida("KG");
        usuario.setTipoLogin("EMAIL");
        usuario.setDataCadastro(LocalDateTime.now());
        usuario = usuarioRepository.save(usuario);

        refeicao = new Refeicao();
        refeicao.setNome("Almoco");
        refeicao.setDataRefeicao(LocalDateTime.now());
        refeicao.setUsuario(usuario);
        refeicao = refeicaoRepository.save(refeicao);

        CategoriaAlimento categoria = categoriaAlimentoRepository.save(new CategoriaAlimento(null, "Graos"));
        Alimento alimento = new Alimento();
        alimento.setNome("Arroz");
        alimento.setKcal("130");
        alimento.setCategoriaAlimento(categoria);
        alimento = alimentoRepository.save(alimento);

        AlimentoRefeicao ar = new AlimentoRefeicao();
        ar.setRefeicao(refeicao);
        ar.setAlimento(alimento);
        ar.setQuantidade(new BigDecimal("100"));
        ar.setPorcao("g");
        alimentoRefeicaoRepository.save(ar);
    }

    @Test
    void findByRefeicaoId_DeveRetornarAlimentosDaRefeicao() {
        List<AlimentoRefeicao> result = alimentoRefeicaoRepository.findByRefeicaoId(refeicao.getId());
        assertEquals(1, result.size());
        assertEquals("Arroz", result.get(0).getAlimento().getNome());
    }

    @Test
    void deleteByRefeicaoId_DeveRemoverVinculos() {
        alimentoRefeicaoRepository.deleteByRefeicaoId(refeicao.getId());
        List<AlimentoRefeicao> result = alimentoRefeicaoRepository.findByRefeicaoId(refeicao.getId());
        assertTrue(result.isEmpty());
    }
}
