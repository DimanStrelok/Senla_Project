package com.senlainc.service;

import com.senlainc.di.Component;
import com.senlainc.di.InjectConstructor;
import com.senlainc.entity.Relation;
import com.senlainc.repository.RelationRepository;

@Component(interfaceClass = RelationService.class)
public class RelationServiceImpl implements RelationService {
    private final RelationRepository relationRepository;

    @InjectConstructor
    public RelationServiceImpl(RelationRepository relationRepository) {
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
