package com.senlainc.repository;

import com.senlainc.entity.GroupChat;
import com.senlainc.entity.GroupChatMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GroupChatRepositoryImpl implements GroupChatRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<GroupChat> getEntityClass() {
        return GroupChat.class;
    }

    @Override
    public List<GroupChatMessage> getMessages(GroupChat groupChat) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select gcm from GroupChatMessage gcm where gcm.chat=:groupChat";
        Query<GroupChatMessage> query = session.createQuery(queryString, GroupChatMessage.class);
        query.setParameter("groupChat", groupChat);
        return query.list();
    }
}
