package com.senlainc.repository;

import com.senlainc.entity.Post;
import com.senlainc.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Class<Post> getEntityClass() {
        return Post.class;
    }

    @Override
    public List<PostComment> getComments(Post post) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select pc from PostComment pc where pc.post=:post";
        Query<PostComment> query = session.createQuery(queryString, PostComment.class);
        query.setParameter("post", post);
        return query.list();
    }
}
