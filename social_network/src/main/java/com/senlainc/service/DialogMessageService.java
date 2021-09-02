package com.senlainc.service;

import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;

public interface DialogMessageService {
    DialogMessageDto create(CreateDialogMessageDto createDialogMessageDto);

    DialogMessageDto get(long id);

    DialogMessageDto changeText(long id, String text);

    void delete(long id);
}
