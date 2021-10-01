package com.senlainc.repository;

import com.senlainc.dto.FindAccountDto;
import com.senlainc.entity.*;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account> {
    Optional<Account> getByEmail(String email);

    List<Account> getAccounts();

    List<Account> findAccounts(FindAccountDto findAccountDto);

    List<Post> getPosts(Account account);

    List<Dialog> getDialogs(Account account);

    List<Account> getFriends(Account account);

    List<FriendInvite> getFriendInvites(Account account);

    List<Group> getGroups(Account account);

    List<GroupInvite> getGroupInvites(Account account);
}
