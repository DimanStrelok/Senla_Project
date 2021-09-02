package com.senlainc.repository;

import com.senlainc.exception.AppException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface CrudRepository<T> {
    default void create(T entity) {
        try {
            Session session = getSessionFactory().getCurrentSession();
            session.save(entity);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    default T get(long id) {
        Session session = getSessionFactory().getCurrentSession();
        return session.byId(getEntityClass()).loadOptional(id)
                .orElseThrow(() -> new AppException("not found '" + getEntityClass() + "' with id = " + id));
    }

    default void update(T entity) {
        try {
            Session session = getSessionFactory().getCurrentSession();
            session.update(entity);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    default void delete(T entity) {
        try {
            Session session = getSessionFactory().getCurrentSession();
            session.remove(entity);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    SessionFactory getSessionFactory();

    Class<T> getEntityClass();
}
