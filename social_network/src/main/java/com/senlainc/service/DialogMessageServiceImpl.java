package com.senlainc.service;

import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.DialogMessageRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DialogMessageServiceImpl implements DialogMessageService, AuthenticationAccess {
    private final DialogMessageRepository repository;
    private final DialogMessageMapper mapper;
    private final AccountRepository accountRepository;
    private final DialogService dialogService;

    @Transactional
    @Override
    public DialogMessageDto create(CreateDialogMessageDto createDialogMessageDto) {
        Account authenticatedAccount = getAuthenticatedAccount();
        Account fromAccount = accountRepository.get(createDialogMessageDto.getFromAccountId());
        Account toAccount = accountRepository.get(createDialogMessageDto.getToAccountId());
        if (authenticatedAccount.getId() != fromAccount.getId()) {
            throw new AccessDeniedException("access to create dialog message from account " + fromAccount + " via account " + authenticatedAccount + " denied");
        }
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
        Account authenticatedAccount = getAuthenticatedAccount();
        DialogMessage entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getDialog().getAccount1().getId() && authenticatedAccount.getId() != entity.getDialog().getAccount2().getId()) {
            throw new AccessDeniedException("access to read dialog message " + entity + " via account " + authenticatedAccount + " denied");
        }
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public DialogMessageDto changeText(long id, String text) {
        Account authenticatedAccount = getAuthenticatedAccount();
        DialogMessage entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId()) {
            throw new AccessDeniedException("access to update dialog message " + entity + " via account " + authenticatedAccount + " denied");
        }
        entity.setText(text);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Account authenticatedAccount = getAuthenticatedAccount();
        DialogMessage entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId()) {
            throw new AccessDeniedException("access to delete dialog message " + entity + " via account " + authenticatedAccount + " denied");
        }
        repository.delete(entity);
    }
}
