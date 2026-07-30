package br.com.nutricao.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import br.com.nutricao.domain.model.CategoriaAlimento;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaAlimentoRepositoryTest {

    @Autowired
    private CategoriaAlimentoRepository categoriaAlimentoRepository;

    @Test
    void save_ComDadosValidos_DevePersistir() {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setNome("Graos");
        CategoriaAlimento saved = categoriaAlimentoRepository.save(categoria);
        assertNotNull(saved.getId());
        assertEquals("Graos", saved.getNome());
    }

    @Test
    void findByNome_QuandoExistir_DeveRetornar() {
        categoriaAlimentoRepository.save(new CategoriaAlimento(null, "Frutas"));
        Optional<CategoriaAlimento> found = categoriaAlimentoRepository.findByNome("Frutas");
        assertTrue(found.isPresent());
    }

    @Test
    void delete_ComIdExistente_DeveRemover() {
        CategoriaAlimento saved = categoriaAlimentoRepository.save(new CategoriaAlimento(null, "Legumes"));
        categoriaAlimentoRepository.deleteById(saved.getId());
        assertFalse(categoriaAlimentoRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void save_ComNomeDuplicado_DeveLancarExcecao() {
        categoriaAlimentoRepository.saveAndFlush(new CategoriaAlimento(null, "Frutas"));

        CategoriaAlimento duplicado = new CategoriaAlimento(null, "Frutas");
        assertThrows(DataIntegrityViolationException.class,
                () -> categoriaAlimentoRepository.saveAndFlush(duplicado));
    }
}
