package com.senlainc.mapper;

import com.senlainc.dto.AccountDto;
import com.senlainc.dto.CreateAccountDto;
import com.senlainc.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto entityToDto(Account entity);

    List<AccountDto> entityListToDtoList(List<Account> entityList);

    Account entityFromDto(CreateAccountDto dto);
}
