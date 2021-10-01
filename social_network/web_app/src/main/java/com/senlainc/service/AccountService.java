package com.senlainc.service;

import com.senlainc.dto.*;

import java.util.List;

public interface AccountService {
    AccountDto create(CreateAccountDto createAccountDto);

    AccountDto get(long id);

    AccountDto update(AccountDto accountDto);

    void delete(long id);

    List<AccountDto> getAccounts();

    List<AccountDto> findAccounts(FindAccountDto findAccountDto);

    List<PostDto> getPosts(long id);

    List<DialogDto> getDialogs(long id);

    List<AccountDto> getFriends(long id);

    List<FriendInviteDto> getFriendInvites(long id);

    List<GroupDto> getGroups(long id);

    List<GroupInviteDto> getGroupInvites(long id);
}
