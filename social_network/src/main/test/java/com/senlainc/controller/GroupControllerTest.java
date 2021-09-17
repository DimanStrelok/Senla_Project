package com.senlainc.controller;

import com.senlainc.dto.AccountDto;
import com.senlainc.dto.CreateGroupDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupDto;
import com.senlainc.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {
    long id = 1;
    String title = "title";
    String description = "description";
    GroupDto dto = new GroupDto();

    @Mock
    GroupService service;
    GroupController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setTitle(title);
        dto.setDescription(description);
        controller = new GroupController(service);
    }

    @Test
    void shouldCreate() {
        long accountId = 1;
        CreateGroupDto createDto = new CreateGroupDto();
        createDto.setAccountId(accountId);
        createDto.setTitle(title);
        createDto.setDescription(description);
        when(service.create(createDto)).thenReturn(dto);
        GroupDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        GroupDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdate() {
        String newTitle = "newTitle";
        dto.setTitle(newTitle);
        when(service.update(dto)).thenReturn(dto);
        GroupDto result = controller.update(dto);
        assertEquals(dto, result);
        verify(service, times(1)).update(dto);
    }

    @Test
    void shouldDelete() {
        doNothing().when(service).delete(id);
        controller.delete(id);
        verify(service, times(1)).delete(id);
    }

    @Test
    void shouldGetGroups() {
        List<GroupDto> groups = new ArrayList<>();
        groups.add(dto);
        when(service.getGroups()).thenReturn(groups);
        List<GroupDto> result = controller.getGroups();
        assertIterableEquals(groups, result);
        verify(service, times(1)).getGroups();
    }

    @Test
    void shouldGetChats() {
        GroupChatDto chat = new GroupChatDto();
        chat.setGroupId(id);
        List<GroupChatDto> chats = new ArrayList<>();
        chats.add(chat);
        when(service.getChats(id)).thenReturn(chats);
        List<GroupChatDto> result = controller.getChats(id);
        assertIterableEquals(chats, result);
        verify(service, times(1)).getChats(id);
    }

    @Test
    void shouldGetMembers() {
        AccountDto member = new AccountDto();
        List<AccountDto> members = new ArrayList<>();
        members.add(member);
        when(service.getMembers(id)).thenReturn(members);
        List<AccountDto> result = controller.getMembers(id);
        assertIterableEquals(members, result);
        verify(service, times(1)).getMembers(id);
    }
}
