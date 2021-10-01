package com.senlainc.mapper;

import com.senlainc.dto.GroupDto;
import com.senlainc.entity.Group;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto entityToDto(Group entity);

    List<GroupDto> entityListToDtoList(List<Group> entityList);
}
