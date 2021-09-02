package com.senlainc.repository;

import com.senlainc.entity.Group;
import com.senlainc.entity.GroupChat;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GroupRepositoryImpl implements GroupRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }

    @Override
    public List<Group> getGroups() {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select g from Group g";
        Query<Group> query = session.createQuery(queryString, Group.class);
        return query.list();
    }

    @Override
    public List<GroupChat> getChats(Group group) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select gc from GroupChat gc where gc.group=:group";
        Query<GroupChat> query = session.createQuery(queryString, GroupChat.class);
        query.setParameter("group", group);
        return query.list();
    }
}
