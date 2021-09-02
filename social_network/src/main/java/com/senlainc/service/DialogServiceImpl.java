package com.senlainc.service;

import com.senlainc.dto.DialogDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.mapper.DialogMapper;
import com.senlainc.mapper.DialogMessageMapper;
import com.senlainc.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DialogServiceImpl implements DialogService {
    private final DialogRepository repository;
    private final DialogMapper mapper;
    private final DialogMessageMapper messageMapper;

    @Transactional(readOnly = true)
    @Override
    public DialogDto get(long id) {
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public void delete(long id) {
        repository.delete(repository.get(id));
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
        Dialog dialog = repository.get(id);
        return messageMapper.entityListToDtoList(repository.getMessages(dialog));
    }
}
