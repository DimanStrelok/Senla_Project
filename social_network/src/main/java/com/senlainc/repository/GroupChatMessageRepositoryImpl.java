package com.senlainc.repository;

import com.senlainc.entity.GroupChatMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GroupChatMessageRepositoryImpl implements GroupChatMessageRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<GroupChatMessage> getEntityClass() {
        return GroupChatMessage.class;
    }
}
