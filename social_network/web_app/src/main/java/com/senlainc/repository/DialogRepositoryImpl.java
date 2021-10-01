package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.entity.Dialog;
import com.senlainc.entity.DialogMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DialogRepositoryImpl implements DialogRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<Dialog> getEntityClass() {
        return Dialog.class;
    }

    @Override
    public Optional<Dialog> get(Account fromAccount, Account toAccount) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select d from Dialog d where (d.account1=:fromAccount and d.account2=:toAccount)" +
                "or (d.account1=:toAccount and d.account2=:fromAccount)";
        Query<Dialog> query = session.createQuery(queryString, Dialog.class);
        query.setParameter("fromAccount", fromAccount);
        query.setParameter("toAccount", toAccount);
        return query.uniqueResultOptional();
    }

    @Override
    public List<DialogMessage> getMessages(Dialog dialog) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select dm from DialogMessage dm where dm.dialog=:dialog";
        Query<DialogMessage> query = session.createQuery(queryString, DialogMessage.class);
        query.setParameter("dialog", dialog);
        return query.list();
    }
}
