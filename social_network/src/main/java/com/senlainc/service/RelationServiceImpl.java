package com.senlainc.service;

import com.senlainc.entity.Account;
import com.senlainc.entity.Relation;
import com.senlainc.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RelationServiceImpl implements RelationService {
    private final RelationRepository repository;

    @Transactional
    @Override
    public void createRelation(Account fromAccount, Account toAccount) {
        Relation relationFromTo = new Relation();
        relationFromTo.setFromAccount(fromAccount);
        relationFromTo.setToAccount(toAccount);
        repository.create(relationFromTo);
        Relation relationToFrom = new Relation();
        relationToFrom.setFromAccount(toAccount);
        relationToFrom.setToAccount(fromAccount);
        repository.create(relationToFrom);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isRelationExist(Account fromAccount, Account toAccount) {
        return repository.isRelationExist(fromAccount, toAccount);
    }
}
