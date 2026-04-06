package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Pedido;
import br.uniesp.si.techback.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

    // LISTAR TODOS
    public List<Pedido> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    // CRIAR
    public Pedido salvar(Pedido pedido) {
        return repository.save(pedido);
    }

    // ATUALIZAR
    public Pedido atualizar(Long id, Pedido pedido) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setId(id);
        return repository.save(pedido);
    }

    // DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado");
        }
        repository.deleteById(id);
    }
}


