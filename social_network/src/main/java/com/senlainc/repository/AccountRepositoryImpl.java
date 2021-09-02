package com.senlainc.repository;

import com.senlainc.dto.FindAccountDto;
import com.senlainc.entity.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public List<Account> getAccounts() {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from Account e";
        Query<Account> query = session.createQuery(queryString, Account.class);
        return query.list();
    }

    @Override
    public List<Account> findAccounts(FindAccountDto findAccountDto) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder sb = new StringBuilder("select e from Account e");
        StringJoiner joiner = new StringJoiner(" and ");
        Map<String, String> params = new HashMap<>();
        if (findAccountDto.getFirstName() != null) {
            params.put("firstName", findAccountDto.getFirstName());
        }
        if (findAccountDto.getLastName() != null) {
            params.put("lastName", findAccountDto.getLastName());
        }
        if (findAccountDto.getMiddleName() != null) {
            params.put("middleName", findAccountDto.getMiddleName());
        }
        if (findAccountDto.getCity() != null) {
            params.put("city", findAccountDto.getCity());
        }
        params.forEach((k, v) -> joiner.add("e." + k + " like :" + k));
        if (!params.isEmpty()) {
            sb.append(" where ");
        }
        sb.append(joiner);
        String queryString = sb.toString();
        Query<Account> query = session.createQuery(queryString, Account.class);
        params.forEach((k, v) -> query.setParameter(k, "%" + v + "%"));
        return query.list();
    }

    @Override
    public List<Post> getPosts(Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from Post e where e.account=:account";
        Query<Post> query = session.createQuery(queryString, Post.class);
        query.setParameter("account", account);
        return query.list();
    }

    @Override
    public List<Dialog> getDialogs(Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from Dialog e where e.account1=:account or e.account2=:account";
        Query<Dialog> query = session.createQuery(queryString, Dialog.class);
        query.setParameter("account", account);
        return query.list();
    }

    @Override
    public List<Account> getFriends(Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from Account e " +
                "join Relation r on e=r.toAccount " +
                "where r.fromAccount=:account";
        Query<Account> query = session.createQuery(queryString, Account.class);
        query.setParameter("account", account);
        return query.list();
    }

    @Override
    public List<FriendInvite> getFriendInvites(Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from FriendInvite e where e.toAccount=:account";
        Query<FriendInvite> query = session.createQuery(queryString, FriendInvite.class);
        query.setParameter("account", account);
        return query.list();
    }

    @Override
    public List<Group> getGroups(Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from Group e " +
                "join GroupAccount ga on e=ga.group " +
                "join Account a on ga.account=a " +
                "where a=:account";
        Query<Group> query = session.createQuery(queryString, Group.class);
        query.setParameter("account", account);
        return query.list();
    }

    @Override
    public List<GroupInvite> getGroupInvites(Account account) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select e from GroupInvite e " +
                "join Group g on e.group=g " +
                "join GroupAccount ga on g=ga.group " +
                "join Account a on ga.account=a " +
                "where ga.role = :role and a=:account";
        Query<GroupInvite> query = session.createQuery(queryString, GroupInvite.class);
        query.setParameter("role", GroupRole.Creator);
        query.setParameter("account", account);
        return query.list();
    }
}
