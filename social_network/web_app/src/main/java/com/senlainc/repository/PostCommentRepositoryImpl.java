package com.senlainc.repository;

import com.senlainc.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostCommentRepositoryImpl implements PostCommentRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<PostComment> getEntityClass() {
        return PostComment.class;
    }
}
