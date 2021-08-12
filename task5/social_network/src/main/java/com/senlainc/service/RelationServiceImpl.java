package com.senlainc.service;

import com.senlainc.entity.Relation;
import com.senlainc.repository.Repository;

public class RelationServiceImpl implements RelationService {
    private final Repository<Relation> relationRepository;

    public RelationServiceImpl(Repository<Relation> relationRepository) {
        this.relationRepository = relationRepository;
    }

    @Override
    public void createRelation(long fromAccountId, long toAccountId) {
        Relation relation1 = new Relation();
        relation1.setFromAccountId(fromAccountId);
        relation1.setToAccountId(toAccountId);
        relationRepository.create(relation1);
        Relation relation2 = new Relation();
        relation2.setFromAccountId(toAccountId);
        relation2.setToAccountId(fromAccountId);
        relationRepository.create(relation2);
    }
}
