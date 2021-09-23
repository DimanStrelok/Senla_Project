package com.senlainc.service;

import com.senlainc.dto.DialogDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.mapper.DialogMapper;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.repository.DialogRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DialogServiceImpl implements DialogService {
    private final DialogRepository repository;
    private final DialogMapper mapper;
    private final DialogMessageMapper messageMapper;
    private final AuthenticationAccess authenticationAccess;

    @Transactional(readOnly = true)
    @Override
    public DialogDto get(long id) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        Dialog entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount1().getId() && authenticatedAccount.getId() != entity.getAccount2().getId()) {
            throw new AccessDeniedException("access to read dialog " + entity + " via account " + authenticatedAccount + " denied");
        }
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public Dialog getOrCreate(Account fromAccount, Account toAccount) {
        return repository.get(fromAccount, toAccount).orElseGet(() -> {
            Dialog dialog = new Dialog();
            dialog.setAccount1(fromAccount);
            dialog.setAccount2(toAccount);
            repository.create(dialog);
            return dialog;
        });
    }

    @Transactional(readOnly = true)
    @Override
    public List<DialogMessageDto> getMessages(long id) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        Dialog entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount1().getId() && authenticatedAccount.getId() != entity.getAccount2().getId()) {
            throw new AccessDeniedException("access to read dialog messages from dialog " + entity + " via account " + authenticatedAccount + " denied");
        }
        return messageMapper.entityListToDtoList(repository.getMessages(entity));
    }
}
