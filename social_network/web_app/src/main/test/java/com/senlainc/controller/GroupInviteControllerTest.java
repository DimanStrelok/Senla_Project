package com.senlainc.controller;

import com.senlainc.dto.CreateGroupInviteDto;
import com.senlainc.dto.GroupInviteDto;
import com.senlainc.entity.InviteStatus;
import com.senlainc.service.GroupInviteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupInviteControllerTest {
    long id = 1;
    long groupId = 1;
    long accountId = 1;
    LocalDateTime createdAt = LocalDateTime.now();
    InviteStatus status = InviteStatus.Created;
    GroupInviteDto dto = new GroupInviteDto();

    @Mock
    GroupInviteService service;
    GroupInviteController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setGroupId(groupId);
        dto.setAccountId(accountId);
        dto.setCreatedAt(createdAt);
        dto.setStatus(status);
        controller = new GroupInviteController(service);
    }

    @Test
    void shouldCreate() {
        CreateGroupInviteDto createDto = new CreateGroupInviteDto();
        createDto.setGroupId(groupId);
        createDto.setAccountId(accountId);
        when(service.create(createDto)).thenReturn(dto);
        GroupInviteDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldAccept() {
        doNothing().when(service).acceptInvite(id);
        controller.acceptInvite(id);
        verify(service, times(1)).acceptInvite(id);
    }

    @Test
    void shouldReject() {
        doNothing().when(service).rejectInvite(id);
        controller.rejectInvite(id);
        verify(service, times(1)).rejectInvite(id);
    }
}
