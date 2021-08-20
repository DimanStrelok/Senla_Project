package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;
import com.senlainc.dto.AccountDto;
import com.senlainc.mapper.AccountMapper;
import com.senlainc.repository.AccountRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component(interfaceClass = AccountService.class)
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @InjectConstructor
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = Mappers.getMapper(AccountMapper.class);
    }

    @Override
    public Optional<AccountDto> findById(long id) {
        return accountRepository.findById(id).map(accountMapper::accountToDto);
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(accountMapper::accountToDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto create(AccountDto account) {
        return accountMapper.accountToDto(accountRepository.create(accountMapper.accountFromDto(account)));
    }

    @Override
    public void update(AccountDto account) {
        accountRepository.update(accountMapper.accountFromDto(account));
    }

    @Override
    public void delete(long id) {
        accountRepository.deleteById(id);
    }
}
