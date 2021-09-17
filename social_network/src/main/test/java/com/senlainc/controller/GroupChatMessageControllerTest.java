package com.senlainc.controller;

import com.senlainc.dto.CreateGroupChatMessageDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.service.GroupChatMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupChatMessageControllerTest {
    long id = 1;
    long chatId = 1;
    long accountId = 1;
    String text = "text";
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime updatedAt = LocalDateTime.now();
    GroupChatMessageDto dto = new GroupChatMessageDto();

    @Mock
    GroupChatMessageService service;
    GroupChatMessageController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setChatId(chatId);
        dto.setAccountId(accountId);
        dto.setText(text);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        controller = new GroupChatMessageController(service);
    }

    @Test
    void shouldCreate() {
        CreateGroupChatMessageDto createDto = new CreateGroupChatMessageDto();
        createDto.setChatId(id);
        createDto.setAccountId(accountId);
        createDto.setText(text);
        when(service.create(createDto)).thenReturn(dto);
        GroupChatMessageDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        GroupChatMessageDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdate() {
        String newText = "newText";
        dto.setText(newText);
        dto.setUpdatedAt(LocalDateTime.now());
        when(service.changeText(id, newText)).thenReturn(dto);
        GroupChatMessageDto result = controller.changeText(id, newText);
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
