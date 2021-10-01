package com.senlainc.service;

import com.senlainc.dto.CreatePostDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto create(CreatePostDto createPostDto);

    PostDto get(long id);

    PostDto changeText(long id, String text);

    void delete(long id);

    List<PostCommentDto> getComments(long id);
}
