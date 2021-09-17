package com.senlainc.controller;

import com.senlainc.dto.DialogDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.service.DialogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DialogControllerTest {
    long id = 1;
    long account1Id = 1;
    long account2Id = 2;
    DialogDto dto = new DialogDto();

    @Mock
    DialogService service;
    DialogController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setAccount1Id(account1Id);
        dto.setAccount2Id(account2Id);
        controller = new DialogController(service);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        DialogDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldGetMessages() {
        DialogMessageDto message = new DialogMessageDto();
        message.setDialogId(id);
        List<DialogMessageDto> messages = new ArrayList<>();
        messages.add(message);
        when(service.getMessages(id)).thenReturn(messages);
        List<DialogMessageDto> result = controller.getMessages(id);
        assertEquals(messages, result);
        verify(service, times(1)).getMessages(id);
    }
}
