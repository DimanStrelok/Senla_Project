package com.senlainc.service;

import com.senlainc.dto.CreatePostCommentDto;
import com.senlainc.dto.PostCommentDto;

public interface PostCommentService {
    PostCommentDto create(CreatePostCommentDto createPostCommentDto);

    PostCommentDto get(long id);

    PostCommentDto changeText(long id, String text);

    void delete(long id);
}
