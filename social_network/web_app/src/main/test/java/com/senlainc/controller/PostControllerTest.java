package com.senlainc.controller;

import com.senlainc.dto.CreatePostDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.dto.PostDto;
import com.senlainc.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    long id = 1;
    long accountId = 1;
    String text = "text";
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime updatedAt = LocalDateTime.now();
    PostDto dto = new PostDto();

    @Mock
    PostService service;
    PostController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setAccountId(accountId);
        dto.setText(text);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        controller = new PostController(service);
    }

    @Test
    void shouldCreate() {
        CreatePostDto createDto = new CreatePostDto();
        createDto.setAccountId(accountId);
        createDto.setText(text);
        when(service.create(createDto)).thenReturn(dto);
        PostDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        PostDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdate() {
        String newText = "newText";
        dto.setText(newText);
        dto.setUpdatedAt(LocalDateTime.now());
        when(service.changeText(id, newText)).thenReturn(dto);
        PostDto result = controller.changeText(id, newText);
        assertEquals(dto, result);
        verify(service, times(1)).changeText(id, newText);
    }

    @Test
    void shouldDelete() {
        doNothing().when(service).delete(id);
        controller.delete(id);
        verify(service, times(1)).delete(id);
    }

    @Test
    void shouldGetComments() {
        PostCommentDto comment = new PostCommentDto();
        comment.setPostId(id);
        List<PostCommentDto> comments = new ArrayList<>();
        comments.add(comment);
        when(service.getComments(id)).thenReturn(comments);
        List<PostCommentDto> result = controller.getComments(id);
        assertEquals(comments, result);
        verify(service, times(1)).getComments(id);
    }
}
