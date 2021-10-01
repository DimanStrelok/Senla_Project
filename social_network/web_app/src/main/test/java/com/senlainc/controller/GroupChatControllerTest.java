package com.senlainc.controller;

import com.senlainc.dto.CreateGroupChatDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.service.GroupChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupChatControllerTest {
    long id = 1;
    long groupId = 1;
    long accountId = 1;
    String title = "title";
    LocalDateTime createdAt = LocalDateTime.now();
    GroupChatDto dto = new GroupChatDto();

    @Mock
    GroupChatService service;
    GroupChatController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setGroupId(groupId);
        dto.setAccountId(accountId);
        dto.setTitle(title);
        dto.setCreatedAt(createdAt);
        controller = new GroupChatController(service);
    }

    @Test
    void shouldCreate() {
        CreateGroupChatDto createDto = new CreateGroupChatDto();
        createDto.setGroupId(groupId);
        createDto.setAccountId(accountId);
        createDto.setTitle(title);
        when(service.create(createDto)).thenReturn(dto);
        GroupChatDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        GroupChatDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdate() {
        String newTitle = "newTitle";
        dto.setTitle(newTitle);
        when(service.changeTitle(id, newTitle)).thenReturn(dto);
        GroupChatDto result = controller.changeTitle(id, newTitle);
        assertEquals(dto, result);
        verify(service, times(1)).changeTitle(id, newTitle);
    }

    @Test
    void shouldDelete() {
        doNothing().when(service).delete(id);
        controller.delete(id);
        verify(service, times(1)).delete(id);
    }

    @Test
    void shouldGetMessages() {
        GroupChatMessageDto message = new GroupChatMessageDto();
        message.setChatId(id);
        List<GroupChatMessageDto> messages = new ArrayList<>();
        messages.add(message);
        when(service.getMessages(id)).thenReturn(messages);
        List<GroupChatMessageDto> result = controller.getMessages(id);
        assertIterableEquals(messages, result);
        verify(service, times(1)).getMessages(id);
    }
}
