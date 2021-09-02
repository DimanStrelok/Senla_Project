package com.senlainc.repository;

import com.senlainc.entity.*;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account> {
    List<Account> getAccounts();

    List<Post> getPosts(Account account);

    List<Dialog> getDialogs(Account account);

    List<Account> getFriends(Account account);

    List<FriendInvite> getFriendInvites(Account account);

    List<Group> getGroups(Account account);

    List<GroupInvite> getGroupInvites(Account account);
}
