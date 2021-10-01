package com.senlainc.controller;

import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.service.DialogMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DialogMessageControllerTest {
    long id = 1;
    long dialogId = 1;
    long accountId = 1;
    String text = "text";
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime updatedAt = LocalDateTime.now();
    DialogMessageDto dto = new DialogMessageDto();

    @Mock
    DialogMessageService service;
    DialogMessageController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setDialogId(dialogId);
        dto.setAccountId(accountId);
        dto.setText(text);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        controller = new DialogMessageController(service);
    }

    @Test
    void shouldCreate() {
        long toAccountId = 2;
        CreateDialogMessageDto createDto = new CreateDialogMessageDto();
        createDto.setFromAccountId(accountId);
        createDto.setToAccountId(toAccountId);
        createDto.setText(text);
        when(service.create(createDto)).thenReturn(dto);
        DialogMessageDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGet() {
        when(service.get(id)).thenReturn(dto);
        DialogMessageDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdate() {
        String newText = "newText";
        dto.setText(newText);
        dto.setUpdatedAt(LocalDateTime.now());
        when(service.changeText(id, newText)).thenReturn(dto);
        DialogMessageDto result = controller.changeText(id, newText);
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
