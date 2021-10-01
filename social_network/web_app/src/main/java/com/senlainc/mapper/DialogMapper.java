package com.senlainc.mapper;

import com.senlainc.dto.DialogDto;
import com.senlainc.entity.Dialog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DialogMapper {
    @Mappings({
            @Mapping(target = "account1Id", source = "entity.account1.id"),
            @Mapping(target = "account2Id", source = "entity.account2.id")
    })
    DialogDto entityToDto(Dialog entity);

    List<DialogDto> entityListToDtoList(List<Dialog> entityList);
}
