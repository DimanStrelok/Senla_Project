package com.senlainc.mapper;

import com.senlainc.dto.PostCommentDto;
import com.senlainc.entity.PostComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostCommentMapper {
    @Mappings({
            @Mapping(target = "postId", source = "entity.post.id"),
            @Mapping(target = "accountId", source = "entity.account.id")
    })
    PostCommentDto entityToDto(PostComment entity);

    List<PostCommentDto> entityListToDtoList(List<PostComment> entityList);
}
