package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupAccount;

public interface GroupAccountRepository extends CrudRepository<GroupAccount> {
    boolean isMember(Group group, Account account);
}
