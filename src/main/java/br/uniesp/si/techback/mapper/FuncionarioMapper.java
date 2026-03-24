package br.uniesp.si.techback.mapper;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.model.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {
    public Funcionario toEntity(FuncionarioDTO dto) {
        if (dto == null) return null;
        return Funcionario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cargo(dto.getCargo())
                .build();
    }

    public FuncionarioDTO toDTO(Funcionario entity) {
        if (entity == null) return null;

        return FuncionarioDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cargo(entity.getCargo())
                .build();
    }
}

