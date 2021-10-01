package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.exception.AppException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrudRepositoryTest {
    CrudRepository<Account> repository;

    @Mock
    SessionFactory sessionFactory;
    @Mock
    Session session;
    @Mock
    IdentifierLoadAccess<Account> identifierLoadAccess;

    @BeforeEach
    void beforeEach() {
        repository = new AccountRepositoryImpl(sessionFactory);
    }

    @Test
    void shouldCreate() {
        long id = 1;
        Account account = new Account();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(account)).thenAnswer((invocation) -> {
            Account entity = invocation.getArgument(0);
            entity.setId(id);
            return id;
        });
        repository.create(account);
        assertEquals(id, account.getId());
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).save(account);
    }

    @Test
    void shouldGet() {
        long id = 1;
        Account account = new Account();
        account.setId(id);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.byId(Account.class)).thenReturn(identifierLoadAccess);
        when(identifierLoadAccess.loadOptional(id)).thenReturn(Optional.of(account));
        Account result = repository.get(id);
        assertEquals(account, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).byId(Account.class);
        verify(identifierLoadAccess, times(1)).loadOptional(id);
    }

    @Test
    void shouldThrowAppExceptionWhenAccountNotFound() {
        long id = 1;
        Account account = new Account();
        account.setId(id);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.byId(Account.class)).thenReturn(identifierLoadAccess);
        when(identifierLoadAccess.loadOptional(id)).thenReturn(Optional.empty());
        assertThrows(AppException.class, () -> repository.get(id));
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).byId(Account.class);
        verify(identifierLoadAccess, times(1)).loadOptional(id);
    }

    @Test
    void shouldUpdate() {
        Account entity = new Account();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).update(entity);
        repository.update(entity);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).update(entity);
    }

    @Test
    void shouldDelete() {
        Account entity = new Account();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).remove(entity);
        repository.delete(entity);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).remove(entity);
    }
}
