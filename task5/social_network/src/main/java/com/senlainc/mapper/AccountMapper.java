package com.senlainc.mapper;

import com.senlainc.dto.AccountDto;
import com.senlainc.entity.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    AccountDto accountToDto(Account account);

    Account accountFromDto(AccountDto dto);
}
