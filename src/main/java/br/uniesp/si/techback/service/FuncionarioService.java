package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.mapper.FuncionarioMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;

    // LISTAR TODOS
    public List<FuncionarioDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // BUSCAR POR ID
    public FuncionarioDTO buscarPorId(Long id) {
        Funcionario func = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return mapper.toDTO(func);
    }

    // CRIAR
    public FuncionarioDTO salvar(FuncionarioDTO dto) {
        Funcionario func = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(func));
    }

    // ATUALIZAR
    public FuncionarioDTO atualizar(Long id, FuncionarioDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        dto.setId(id);
        Funcionario func = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(func));
    }

    // DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado");
        }
        repository.deleteById(id);
    }
}

