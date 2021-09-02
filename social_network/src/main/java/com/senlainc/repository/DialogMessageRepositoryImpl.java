package com.senlainc.repository;

import com.senlainc.entity.DialogMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DialogMessageRepositoryImpl implements DialogMessageRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<DialogMessage> getEntityClass() {
        return DialogMessage.class;
    }
}
