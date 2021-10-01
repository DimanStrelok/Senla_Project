package com.senlainc.controller;

import com.senlainc.dto.CreatePostCommentDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.service.PostCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostCommentControllerTest {
    long id = 1;
    long postId = 1;
    long accountId = 1;
    String text = "text";
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime updatedAt = LocalDateTime.now();
    PostCommentDto dto = new PostCommentDto();

    @Mock
    PostCommentService service;
    PostCommentController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setPostId(postId);
        dto.setAccountId(accountId);
        dto.setText(text);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        controller = new PostCommentController(service);
    }

    @Test
    void shouldCreate() {
        CreatePostCommentDto createDto = new CreatePostCommentDto();
        createDto.setPostId(postId);
        createDto.setAccountId(accountId);
        createDto.setText(text);
        when(service.create(createDto)).thenReturn(dto);
        PostCommentDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        PostCommentDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdate() {
        String newText = "newText";
        dto.setText(newText);
        dto.setUpdatedAt(LocalDateTime.now());
        when(service.changeText(id, newText)).thenReturn(dto);
        PostCommentDto result = controller.changeText(id, newText);
        assertEquals(dto, result);
        verify(service, times(1)).changeText(id, newText);
    }

    @Test
    void shouldDelete() {
        doNothing().when(service).delete(id);
        controller.delete(id);
        verify(service, times(1)).delete(id);
    }
}
