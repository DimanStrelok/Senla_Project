package com.senlainc.service;

import com.senlainc.dto.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDto> findById(long id);

    List<AccountDto> findAll();

    AccountDto create(AccountDto account);

    void update(AccountDto account);

    void delete(long id);
}
