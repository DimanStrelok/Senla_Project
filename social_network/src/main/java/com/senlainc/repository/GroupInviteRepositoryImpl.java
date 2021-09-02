package com.senlainc.repository;

import com.senlainc.entity.GroupInvite;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GroupInviteRepositoryImpl implements GroupInviteRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<GroupInvite> getEntityClass() {
        return GroupInvite.class;
    }
}
