package com.senlainc.mapper;

import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.FriendInvite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendInviteMapper {
    @Mappings({
            @Mapping(target = "fromAccountId", source = "entity.fromAccount.id"),
            @Mapping(target = "toAccountId", source = "entity.toAccount.id")
    })
    FriendInviteDto entityToDto(FriendInvite entity);

    List<FriendInviteDto> entityListToDtoList(List<FriendInvite> entityList);
}
