package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Group;
import com.senlainc.entity.GroupAccount;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GroupAccountRepositoryImpl implements GroupAccountRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<GroupAccount> getEntityClass() {
        return GroupAccount.class;
    }

    @Override
    public boolean isMember(Group group, Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from GroupAccount e where e.group=:group and e.account=:account";
        Query<GroupAccount> query = session.createQuery(queryString, GroupAccount.class);
        query.setParameter("group", group);
        query.setParameter("account", account);
        return query.uniqueResultOptional().isPresent();
    }
}
