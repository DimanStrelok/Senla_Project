package com.senlainc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.AppConfig;
import com.senlainc.TestConfig;
import com.senlainc.dto.AccountDto;
import com.senlainc.dto.CreateAccountDto;
import com.senlainc.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void endPointAccessTests() throws Exception {
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
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(createAccountDto);
        mockMvc.perform(post("/account")
                        .with(anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        mockMvc.perform(get("/account")).andExpect(unauthenticated());
        mockMvc.perform(get("/account").with(user("email"))).andExpect(authenticated());
    }

    @Mock
    AccountService accountService;

    @Test
    void shouldCreateAccount() {
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
        AccountDto accountDto = new AccountDto();
        accountDto.setFirstName(firstName);
        accountDto.setLastName(lastName);
        accountDto.setMiddleName(middleName);
        accountDto.setEmail(email);
        accountDto.setPhoneNumber(phoneNumber);
        accountDto.setPassword(password);
        accountDto.setCity(city);
        AccountController accountController = new AccountController(accountService);
        when(accountService.create(createAccountDto)).thenReturn(accountDto);
        AccountDto result = accountController.create(createAccountDto);
        assertEquals(firstName, result.getFirstName());
        verify(accountService, times(1)).create(createAccountDto);
    }
}
