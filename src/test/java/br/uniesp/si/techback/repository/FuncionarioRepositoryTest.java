package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Funcionario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Testes do FuncionarioRepository")
class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository repository;

    @Test
    @DisplayName("Deve salvar funcionário")
    void deveSalvarFuncionario() {

        Funcionario funcionario = Funcionario.builder()
                .nome("Arthur")
                .cargo("Dev")
                .build();

        Funcionario salvo = repository.save(funcionario);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("Arthur");
    }

    @Test
    @DisplayName("Deve buscar funcionário por ID")
    void deveBuscarPorId() {

        Funcionario funcionario = Funcionario.builder()
                .nome("Arthur")
                .cargo("Dev")
                .build();

        Funcionario salvo = repository.save(funcionario);

        Optional<Funcionario> encontrado = repository.findById(salvo.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Arthur");
    }

    @Test
    @DisplayName("Deve deletar funcionário")
    void deveDeletar() {

        Funcionario funcionario = Funcionario.builder()
                .nome("Arthur")
                .cargo("Dev")
                .build();

        Funcionario salvo = repository.save(funcionario);

        repository.deleteById(salvo.getId());

        Optional<Funcionario> resultado = repository.findById(salvo.getId());

        assertThat(resultado).isEmpty();
    }
}
