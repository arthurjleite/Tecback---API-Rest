package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Cliente;
import br.uniesp.si.techback.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    // LISTAR TODOS
    public List<Cliente> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    // CRIAR
    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    // ATUALIZAR
    public Cliente atualizar(Long id, Cliente cliente) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setId(id);
        return repository.save(cliente);
    }

    // DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        repository.deleteById(id);
    }
}

