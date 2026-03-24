package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.mapper.FuncionarioMapper;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do FuncionarioService")
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @Mock
    private FuncionarioMapper mapper;

    @InjectMocks
    private FuncionarioService service;

    private Funcionario funcionario;
    private FuncionarioDTO dto;

    @BeforeEach
    void setUp() {
        funcionario = Funcionario.builder()
                .id(1L)
                .nome("Arthur")
                .cargo("Dev")
                .build();

        dto = FuncionarioDTO.builder()
                .id(1L)
                .nome("Arthur")
                .cargo("Dev")
                .build();
    }

    // 🔹 LISTAR
    @Test
    @DisplayName("Deve listar todos os funcionários")
    void deveListar() {
        when(repository.findAll()).thenReturn(List.of(funcionario));
        when(mapper.toDTO(funcionario)).thenReturn(dto);

        List<FuncionarioDTO> resultado = service.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Arthur");
        verify(repository).findAll();
        verify(mapper).toDTO(funcionario);
    }

    // 🔹 BUSCAR POR ID
    @Test
    @DisplayName("Deve buscar funcionário por ID")
    void deveBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(mapper.toDTO(funcionario)).thenReturn(dto);

        FuncionarioDTO resultado = service.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(repository).findById(1L);
    }

    // 🔹 ERRO AO BUSCAR
    @Test
    @DisplayName("Deve lançar erro ao buscar ID inexistente")
    void erroBuscarPorId() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(999L))
                .isInstanceOf(RuntimeException.class);

        verify(repository).findById(999L);
    }

    // 🔹 SALVAR
    @Test
    @DisplayName("Deve salvar funcionário")
    void deveSalvar() {
        when(mapper.toEntity(dto)).thenReturn(funcionario);
        when(repository.save(funcionario)).thenReturn(funcionario);
        when(mapper.toDTO(funcionario)).thenReturn(dto);

        FuncionarioDTO resultado = service.salvar(dto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Arthur");
        verify(repository).save(funcionario);
    }

    // 🔹 ATUALIZAR
    @Test
    @DisplayName("Deve atualizar funcionário")
    void deveAtualizar() {
        when(repository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(mapper.toEntity(dto)).thenReturn(funcionario);
        when(repository.save(funcionario)).thenReturn(funcionario);
        when(mapper.toDTO(funcionario)).thenReturn(dto);

        FuncionarioDTO resultado = service.atualizar(1L, dto);

        assertThat(resultado).isNotNull();
        verify(repository).findById(1L);
        verify(repository).save(funcionario);
    }

    // 🔹 ERRO AO ATUALIZAR
    @Test
    @DisplayName("Erro ao atualizar inexistente")
    void erroAtualizar() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(999L, dto))
                .isInstanceOf(RuntimeException.class);
    }

    // 🔹 DELETAR
    @Test
    @DisplayName("Deve deletar funcionário")
    void deveDeletar() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository).deleteById(1L);
    }

    // 🔹 ERRO AO DELETAR
    @Test
    @DisplayName("Erro ao deletar inexistente")
    void erroDeletar() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(999L))
                .isInstanceOf(RuntimeException.class);

        verify(repository, never()).deleteById(any());
    }
}
