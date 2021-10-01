package com.senlainc.mapper;

import com.senlainc.dto.PostDto;
import com.senlainc.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mappings({
            @Mapping(target = "accountId", source = "entity.account.id")
    })
    PostDto entityToDto(Post entity);

    List<PostDto> entityListToDtoList(List<Post> entityList);
}
