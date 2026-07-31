package br.com.nutricao.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.Alimento;
import br.com.nutricao.domain.AlimentoFavorito;
import br.com.nutricao.domain.CategoriaAlimento;
import br.com.nutricao.domain.Usuario;

@DataJpaTest
@ActiveProfiles("test")
class AlimentoFavoritoRepositoryTest {

    @Autowired
    private AlimentoFavoritoRepository alimentoFavoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private CategoriaAlimentoRepository categoriaAlimentoRepository;

    private Usuario usuario;
    private Alimento alimento;

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

        CategoriaAlimento categoria = categoriaAlimentoRepository.save(new CategoriaAlimento(null, "Frutas"));
        alimento = new Alimento();
        alimento.setNome("Maca");
        alimento.setCategoriaAlimento(categoria);
        alimento = alimentoRepository.save(alimento);
    }

    @Test
    void save_ComDadosValidos_DevePersistir() {
        AlimentoFavorito favorito = new AlimentoFavorito();
        favorito.setUsuario(usuario);
        favorito.setAlimento(alimento);
        favorito.setDataAdicao(LocalDateTime.now());
        AlimentoFavorito saved = alimentoFavoritoRepository.save(favorito);
        assertNotNull(saved.getId());
    }

    @Test
    void findByUsuarioId_DeveRetornarFavoritos() {
        AlimentoFavorito favorito = new AlimentoFavorito();
        favorito.setUsuario(usuario);
        favorito.setAlimento(alimento);
        favorito.setDataAdicao(LocalDateTime.now());
        alimentoFavoritoRepository.save(favorito);

        List<AlimentoFavorito> result = alimentoFavoritoRepository.findByUsuarioIdOrderByDataAdicaoDesc(usuario.getId());
        assertEquals(1, result.size());
        assertEquals("Maca", result.get(0).getAlimento().getNome());
    }

    @Test
    void existsByUsuarioIdAndAlimentoId_DeveRetornarTrueSeExistir() {
        AlimentoFavorito favorito = new AlimentoFavorito();
        favorito.setUsuario(usuario);
        favorito.setAlimento(alimento);
        favorito.setDataAdicao(LocalDateTime.now());
        alimentoFavoritoRepository.save(favorito);

        boolean exists = alimentoFavoritoRepository.existsByUsuarioIdAndAlimentoId(usuario.getId(), alimento.getId());
        assertTrue(exists);
    }
}
