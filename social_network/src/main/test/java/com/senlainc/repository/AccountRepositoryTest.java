package com.senlainc.repository;

import com.senlainc.entity.Account;
import com.senlainc.exception.AppException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
class AccountRepositoryTest {
    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Query<Account> accountQuery;

    AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
        accountRepository = new AccountRepositoryImpl(sessionFactory);
    }

    @Test
    void duringAccountCreationIdIsSet() {
        long accountId = 1;
        String firstName = "firstName";
        Account account = new Account();
        account.setFirstName(firstName);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(account)).thenAnswer((invocation) -> {
            Account entity = invocation.getArgument(0);
            entity.setId(accountId);
            return accountId;
        });
        accountRepository.create(account);
        assertEquals(accountId, account.getId());
        assertEquals(firstName, account.getFirstName());
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).save(account);
    }

    @Test
    void shouldThrowAppExceptionIfHappenedHibernateException() {
        Account account = new Account();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(account)).thenThrow(HibernateException.class);
        assertThrows(AppException.class, () -> accountRepository.create(account));
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).save(account);
    }

    @Test
    void shouldFindAccountByEmail() {
        String email = "email";
        Account account = new Account();
        account.setEmail(email);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Account.class))).thenReturn(accountQuery);
        when(accountQuery.uniqueResultOptional()).thenReturn(Optional.of(account));
        Optional<Account> result = accountRepository.getByEmail(email);
        assertEquals(Optional.of(email), result.map(Account::getEmail));
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery(anyString(), eq(Account.class));
        verify(accountQuery, times(1)).uniqueResultOptional();
    }

    @Test
    void shouldNotFoundAccountByEmail() {
        String email = "email";
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Account.class))).thenReturn(accountQuery);
        when(accountQuery.uniqueResultOptional()).thenReturn(Optional.empty());
        Optional<Account> result = accountRepository.getByEmail(email);
        assertEquals(Optional.empty(), result);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery(anyString(), eq(Account.class));
        verify(accountQuery, times(1)).uniqueResultOptional();
    }
}
