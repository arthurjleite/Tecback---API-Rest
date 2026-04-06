package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Produto;
import br.uniesp.si.techback.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    // LISTAR TODOS
    public List<Produto> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // CRIAR
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    // ATUALIZAR
    public Produto atualizar(Long id, Produto produto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setId(id);
        return repository.save(produto);
    }

    // DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}

