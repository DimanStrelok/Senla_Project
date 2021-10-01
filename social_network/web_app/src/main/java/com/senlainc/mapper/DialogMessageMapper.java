package com.senlainc.mapper;

import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.DialogMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DialogMessageMapper {
    @Mappings({
            @Mapping(target = "dialogId", source = "entity.dialog.id"),
            @Mapping(target = "accountId", source = "entity.account.id")
    })
    DialogMessageDto entityToDto(DialogMessage entity);

    List<DialogMessageDto> entityListToDtoList(List<DialogMessage> entityList);
}
