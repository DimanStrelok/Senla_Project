package com.senlainc.repository;

import com.senlainc.entity.FriendInvite;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FriendInviteRepositoryImpl implements FriendInviteRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<FriendInvite> getEntityClass() {
        return FriendInvite.class;
    }
}
