package com.senlainc.mapper;

import com.senlainc.dto.GroupInviteDto;
import com.senlainc.entity.GroupInvite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupInviteMapper {
    @Mappings({
            @Mapping(target = "groupId", source = "entity.group.id"),
            @Mapping(target = "accountId", source = "entity.account.id")
    })
    GroupInviteDto entityToDto(GroupInvite entity);

    List<GroupInviteDto> entityListToDtoList(List<GroupInvite> entityList);
}
