package com.senlainc.controller;

import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.entity.InviteStatus;
import com.senlainc.service.FriendInviteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendInviteControllerTest {
    long id = 1;
    long fromAccountId = 1;
    long toAccountId = 2;
    LocalDateTime createdAt = LocalDateTime.now();
    InviteStatus status = InviteStatus.Created;
    FriendInviteDto dto = new FriendInviteDto();

    @Mock
    FriendInviteService service;
    FriendInviteController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setFromAccountId(fromAccountId);
        dto.setToAccountId(toAccountId);
        dto.setCreatedAt(createdAt);
        dto.setStatus(status);
        controller = new FriendInviteController(service);
    }

    @Test
    void shouldCreate() {
        CreateFriendInviteDto createDto = new CreateFriendInviteDto();
        createDto.setFromAccountId(fromAccountId);
        createDto.setToAccountId(toAccountId);
        when(service.create(createDto)).thenReturn(dto);
        FriendInviteDto result = controller.create(createDto);
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
