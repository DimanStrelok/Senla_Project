package com.senlainc.service;

import com.senlainc.dto.AccountDto;
import com.senlainc.dto.CreateAccountDto;
import com.senlainc.entity.Account;
import com.senlainc.mapper.*;
import com.senlainc.repository.AccountRepository;
import liquibase.pro.packaged.A;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class AccountServiceImplTest {
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    private final DialogMapper dialogMapper = Mappers.getMapper(DialogMapper.class);
    private final FriendInviteMapper friendInviteMapper = Mappers.getMapper(FriendInviteMapper.class);
    private final GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);
    private final GroupInviteMapper groupInviteMapper = Mappers.getMapper(GroupInviteMapper.class);

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountMapper accountMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {

    }

    @Test
    void shouldCreateAccountWithPasswordEncode() {
        assertNotNull(postMapper);
        String firstName = "firstName";
        String lastName = "lastName";
        String middleName = "middleName";
        String email = "email";
        String phoneNumber = "phoneNumber";
        String password = "password";
        String city = "city";
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setFirstName(firstName);
        createAccountDto.setLastName(lastName);
        createAccountDto.setMiddleName(middleName);
        createAccountDto.setEmail(email);
        createAccountDto.setPhoneNumber(phoneNumber);
        createAccountDto.setPassword(password);
        createAccountDto.setCity(city);
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setMiddleName(middleName);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);
        account.setPassword(password);
        account.setCity(city);
        AccountDto accountDto = new AccountDto();
        accountDto.setFirstName(firstName);
        accountDto.setLastName(lastName);
        accountDto.setMiddleName(middleName);
        accountDto.setEmail(email);
        accountDto.setPhoneNumber(phoneNumber);
        accountDto.setPassword(password);
        accountDto.setCity(city);
        doNothing().when(accountRepository).create(account);
        when(accountMapper.entityFromDto(createAccountDto)).thenReturn(account);
        when(accountMapper.entityToDto(account)).thenReturn(accountDto);
        when(passwordEncoder.encode(password)).thenReturn(password);
        AccountService accountService = new AccountServiceImpl(
                accountRepository, accountMapper, postMapper, dialogMapper,
                friendInviteMapper, groupMapper, groupInviteMapper, passwordEncoder
        );
        AccountDto result = accountService.create(createAccountDto);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(middleName, result.getMiddleName());
        assertEquals(email, result.getEmail());
        assertEquals(phoneNumber, result.getPhoneNumber());
        assertEquals(password, result.getPassword());
        assertEquals(city, result.getCity());
        verify(accountMapper, times(1)).entityFromDto(createAccountDto);
        verify(passwordEncoder, times(1)).encode(password);
        verify(accountRepository, times(1)).create(account);
        verify(accountMapper, times(1)).entityToDto(account);
    }

    @Test
    void get() {
    }
}
