package com.senlainc.service;

import com.senlainc.dto.DialogDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;

import java.util.List;

public interface DialogService {
    DialogDto get(long id);

    void delete(long id);

    Dialog getOrCreate(Account fromAccount, Account toAccount);

    List<DialogMessageDto> getMessages(long id);
}
