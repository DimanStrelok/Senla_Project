package com.senlainc.service;

import com.senlainc.entity.Account;

public interface RelationService {
    void createRelation(Account fromAccount, Account toAccount);

    boolean isRelationExist(Account fromAccount, Account toAccount);
}
