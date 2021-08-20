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
        Relation relationFromTo = new Relation();
        relationFromTo.setFromAccountId(fromAccountId);
        relationFromTo.setToAccountId(toAccountId);
        relationRepository.create(relationFromTo);
        Relation relationToFrom = new Relation();
        relationToFrom.setFromAccountId(toAccountId);
        relationToFrom.setToAccountId(fromAccountId);
        relationRepository.create(relationToFrom);
    }
}
