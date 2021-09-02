package com.senlainc.mapper;

import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.entity.GroupChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupChatMessageMapper {
    @Mappings({
            @Mapping(target = "chatId", source = "entity.chat.id"),
            @Mapping(target = "accountId", source = "entity.account.id")
    })
    GroupChatMessageDto entityToDto(GroupChatMessage entity);

    List<GroupChatMessageDto> entityListToDtoList(List<GroupChatMessage> entityList);
}
