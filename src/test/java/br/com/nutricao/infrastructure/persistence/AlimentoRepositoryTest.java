package br.com.nutricao.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.model.Alimento;
import br.com.nutricao.domain.model.CategoriaAlimento;

@DataJpaTest
@ActiveProfiles("test")
class AlimentoRepositoryTest {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private CategoriaAlimentoRepository categoriaAlimentoRepository;

    private CategoriaAlimento categoria;

    @BeforeEach
    void setUp() {
        categoria = categoriaAlimentoRepository.save(new CategoriaAlimento(null, "Graos"));
    }

    @Test
    void save_ComDadosValidos_DevePersistir() {
        Alimento alimento = new Alimento();
        alimento.setNome("Arroz");
        alimento.setKcal("130");
        alimento.setCategoriaAlimento(categoria);
        Alimento saved = alimentoRepository.save(alimento);
        assertNotNull(saved.getId());
        assertEquals("Arroz", saved.getNome());
    }

    @Test
    void findByCategoriaAlimentoId_DeveRetornarAlimentosDaCategoria() {
        alimentoRepository.save(criarAlimento("Arroz"));
        alimentoRepository.save(criarAlimento("Feijao"));
        CategoriaAlimento outraCategoria = categoriaAlimentoRepository.save(new CategoriaAlimento(null, "Frutas"));
        alimentoRepository.save(new Alimento(null, "Maca", null, null, null, null, null, null, outraCategoria));
        List<Alimento> result = alimentoRepository.findByCategoriaAlimentoId(categoria.getId());
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(a -> a.getCategoriaAlimento().getId().equals(categoria.getId())));
    }

    @Test
    void findByNomeContaining_DeveRetornarAlimentosComONome() {
        alimentoRepository.save(criarAlimento("Arroz branco"));
        alimentoRepository.save(criarAlimento("Arroz integral"));
        alimentoRepository.save(criarAlimento("Feijao preto"));
        List<Alimento> result = alimentoRepository.findByNomeContaining("Arroz");
        assertEquals(2, result.size());
    }

    private Alimento criarAlimento(String nome) {
        Alimento alimento = new Alimento();
        alimento.setNome(nome);
        alimento.setKcal("100");
        alimento.setCategoriaAlimento(categoria);
        return alimento;
    }
}
