package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Relation;

public interface RelationRepository extends CrudRepository<Relation> {
    boolean isRelationExist(Account fromAccount, Account toAccount);
}
