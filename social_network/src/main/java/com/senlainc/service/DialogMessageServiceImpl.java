package com.senlainc.service;

import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.DialogMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DialogMessageServiceImpl implements DialogMessageService {
    private final DialogMessageRepository repository;
    private final DialogMessageMapper mapper;
    private final AccountRepository accountRepository;
    private final DialogService dialogService;

    @Transactional
    @Override
    public DialogMessageDto create(CreateDialogMessageDto createDialogMessageDto) {
        Account fromAccount = accountRepository.get(createDialogMessageDto.getFromAccountId());
        Account toAccount = accountRepository.get(createDialogMessageDto.getToAccountId());
        Dialog dialog = dialogService.getOrCreate(fromAccount, toAccount);
        LocalDateTime now = LocalDateTime.now();
        DialogMessage entity = new DialogMessage();
        entity.setDialog(dialog);
        entity.setAccount(fromAccount);
        entity.setText(createDialogMessageDto.getText());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public DialogMessageDto get(long id) {
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public DialogMessageDto changeText(long id, String text) {
        DialogMessage entity = repository.get(id);
        entity.setText(text);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        repository.delete(repository.get(id));
    }
}
