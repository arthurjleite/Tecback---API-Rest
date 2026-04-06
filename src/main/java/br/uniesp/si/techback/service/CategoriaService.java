package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Categoria;
import br.uniesp.si.techback.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    // LISTAR TODOS
    public List<Categoria> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));
    }

    // CRIAR
    public Categoria salvar(Categoria categoria) {
        return repository.save(categoria);
    }

    // ATUALIZAR
    public Categoria atualizar(Long id, Categoria categoria) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));

        categoria.setId(id);
        return repository.save(categoria);
    }

    // DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrado");
        }
        repository.deleteById(id);
    }
}



