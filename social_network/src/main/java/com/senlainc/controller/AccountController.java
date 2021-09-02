package com.senlainc.controller;

import com.senlainc.dto.*;
import com.senlainc.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/account")
@RestController
public class AccountController {
    private static final Logger log = LogManager.getLogger(AccountController.class);
    private final AccountService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto create(@RequestBody CreateAccountDto createAccountDto) {
        log.info("Request: 'create', params: {}", createAccountDto);
        return service.create(createAccountDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto update(@RequestBody AccountDto accountDto) {
        log.info("Request: 'update', params: {}", accountDto);
        return service.update(accountDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> getAccounts() {
        log.info("Request: 'getAccounts'");
        return service.getAccounts();
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> findAccounts(@RequestBody FindAccountDto findAccountDto) {
        log.info("Request: 'findAccounts', params: {}", findAccountDto);
        return service.findAccounts(findAccountDto);
    }

    @GetMapping(value = "/{id}/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> getPosts(@PathVariable long id) {
        log.info("Request: 'getPosts', params: {}", id);
        return service.getPosts(id);
    }

    @GetMapping(value = "/{id}/dialog", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DialogDto> getDialogs(@PathVariable long id) {
        log.info("Request: 'getDialogs', params: {}", id);
        return service.getDialogs(id);
    }

    @GetMapping(value = "/{id}/friend", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDto> getFriends(@PathVariable long id) {
        log.info("Request: 'getFriends', params: {}", id);
        return service.getFriends(id);
    }

    @GetMapping(value = "/{id}/friend/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FriendInviteDto> getFriendInvites(@PathVariable long id) {
        log.info("Request: 'getFriendInvites', params: {}", id);
        return service.getFriendInvites(id);
    }

    @GetMapping(value = "/{id}/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroups(@PathVariable long id) {
        log.info("Request: 'getGroups', params: {}", id);
        return service.getGroups(id);
    }

    @GetMapping(value = "/{id}/group/invite", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupInviteDto> getGroupInvites(@PathVariable long id) {
        log.info("Request: 'getGroupInvites', params: {}", id);
        return service.getGroupInvites(id);
    }
}
