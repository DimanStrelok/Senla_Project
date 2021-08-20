package com.senlainc.factory;

import com.senlainc.entity.Account;
import com.senlainc.entity.FriendInvite;
import com.senlainc.entity.Relation;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.FriendInviteRepository;
import com.senlainc.repository.RelationRepository;
import com.senlainc.repository.Repository;
import com.senlainc.service.*;

public class ServiceFactory {
    public static AccountService accountService() {
        Repository<Account> accountRepository = new AccountRepository();
        return new AccountServiceImpl(accountRepository);
    }

    public static FriendInviteService friendInviteService() {
        Repository<FriendInvite> friendInviteRepository = new FriendInviteRepository();
        return new FriendInviteServiceImpl(friendInviteRepository, relationService());
    }

    public static RelationService relationService() {
        Repository<Relation> relationRepository = new RelationRepository();
        return new RelationServiceImpl(relationRepository);
    }
}
