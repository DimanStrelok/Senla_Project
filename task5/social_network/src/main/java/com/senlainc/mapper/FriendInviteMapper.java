package com.senlainc.mapper;

import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.FriendInvite;
import org.mapstruct.Mapper;

@Mapper
public interface FriendInviteMapper {
    FriendInviteDto friendInviteToDto(FriendInvite entity);

    FriendInvite friendInviteFromDto(FriendInviteDto dto);
}
