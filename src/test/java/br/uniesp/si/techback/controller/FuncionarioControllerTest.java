package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
@DisplayName("Testes do FuncionarioController")
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    private FuncionarioDTO dto;
    private FuncionarioDTO dtoSalvo;

    @BeforeEach
    void setUp() {
        dto = FuncionarioDTO.builder()
                .nome("Arthur")
                .cargo("Dev")
                .build();

        dtoSalvo = FuncionarioDTO.builder()
                .id(1L)
                .nome("Arthur")
                .cargo("Dev")
                .build();
    }

    // 🔹 LISTAR
    @Test
    @DisplayName("Deve listar funcionários")
    void deveListar() throws Exception {
        when(service.listar()).thenReturn(List.of(dtoSalvo));

        mockMvc.perform(get("/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Arthur"));
    }

    // 🔹 BUSCAR
    @Test
    @DisplayName("Deve buscar por ID")
    void deveBuscarPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(dtoSalvo);

        mockMvc.perform(get("/funcionarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Arthur"));
    }

    // 🔹 404
    @Test
    @DisplayName("Deve retornar 404 ao buscar inexistente")
    void erroBuscar() throws Exception {
        when(service.buscarPorId(999L))
                .thenThrow(new RuntimeException("Não encontrado"));

        mockMvc.perform(get("/funcionarios/999"))
                .andExpect(status().isNotFound());
    }

    // 🔹 CRIAR
    @Test
    @DisplayName("Deve criar funcionário")
    void deveCriar() throws Exception {
        when(service.salvar(any(FuncionarioDTO.class))).thenReturn(dtoSalvo);

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(header().exists("Location"));
    }

    // 🔹 VALIDAÇÃO
    @Test
    @DisplayName("Deve retornar 400 ao criar inválido")
    void erroValidacao() throws Exception {
        FuncionarioDTO invalido = FuncionarioDTO.builder()
                .nome("") // inválido
                .build();

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    // 🔹 ATUALIZAR
    @Test
    @DisplayName("Deve atualizar funcionário")
    void deveAtualizar() throws Exception {
        FuncionarioDTO atualizado = FuncionarioDTO.builder()
                .id(1L)
                .nome("Arthur Atualizado")
                .cargo("Senior")
                .build();

        when(service.atualizar(eq(1L), any(FuncionarioDTO.class)))
                .thenReturn(atualizado);

        mockMvc.perform(put("/funcionarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Arthur Atualizado"));
    }

    // 🔹 ERRO UPDATE
    @Test
    @DisplayName("Deve retornar 404 ao atualizar inexistente")
    void erroAtualizar() throws Exception {
        when(service.atualizar(eq(999L), any(FuncionarioDTO.class)))
                .thenThrow(new RuntimeException("Não encontrado"));

        mockMvc.perform(put("/funcionarios/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // 🔹 DELETE
    @Test
    @DisplayName("Deve deletar funcionário")
    void deveDeletar() throws Exception {
        mockMvc.perform(delete("/funcionarios/1"))
                .andExpect(status().isNoContent());
    }

    // 🔹 DELETE inexistente (mesmo padrão do Filme)
    @Test
    @DisplayName("Deve retornar 204 mesmo se não existir")
    void deletarInexistente() throws Exception {
        mockMvc.perform(delete("/funcionarios/999"))
                .andExpect(status().isNoContent());
    }
}