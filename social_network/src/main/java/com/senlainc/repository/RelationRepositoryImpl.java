package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Relation;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RelationRepositoryImpl implements RelationRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<Relation> getEntityClass() {
        return Relation.class;
    }

    @Override
    public boolean isRelationExist(Account fromAccount, Account toAccount) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select r from Relation r where r.fromAccount=:fromAccount and r.toAccount=:toAccount";
        Query<Relation> query = session.createQuery(queryString, Relation.class);
        query.setParameter("fromAccount", fromAccount);
        query.setParameter("toAccount", toAccount);
        return query.uniqueResultOptional().isPresent();
    }
}
