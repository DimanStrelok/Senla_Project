package com.senlainc.mapper;

import com.senlainc.dto.GroupChatDto;
import com.senlainc.entity.GroupChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupChatMapper {
    @Mappings({
            @Mapping(target = "groupId", source = "entity.group.id"),
            @Mapping(target = "accountId", source = "entity.account.id")
    })
    GroupChatDto entityToDto(GroupChat entity);

    List<GroupChatDto> entityListToDtoList(List<GroupChat> entityList);
}
