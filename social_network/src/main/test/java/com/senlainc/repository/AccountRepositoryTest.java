package com.senlainc.repository;

import com.senlainc.dto.FindAccountDto;
import com.senlainc.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {
    Account account = new Account();
    AccountRepository repository;

    @Mock
    SessionFactory sessionFactory;
    @Mock
    Session session;
    @Mock
    Query<Account> accountQuery;
    @Mock
    Query<Post> postQuery;
    @Mock
    Query<Dialog> dialogQuery;
    @Mock
    Query<FriendInvite> friendInviteQuery;
    @Mock
    Query<Group> groupQuery;
    @Mock
    Query<GroupInvite> groupInviteQuery;

    @BeforeEach
    void beforeEach() {
        repository = new AccountRepositoryImpl(sessionFactory);
    }

    @Test
    void shouldFindAccountByEmail() {
        String email = "email";
        Account account = new Account();
        account.setEmail(email);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Account.class))).thenReturn(accountQuery);
        when(accountQuery.uniqueResultOptional()).thenReturn(Optional.of(account));
        Optional<Account> result = repository.getByEmail(email);
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
        Optional<Account> result = repository.getByEmail(email);
        assertEquals(Optional.empty(), result);
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery(anyString(), eq(Account.class));
        verify(accountQuery, times(1)).uniqueResultOptional();
    }

    @Test
    void shouldGetAccounts() {
        List<Account> accounts = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Account.class))).thenReturn(accountQuery);
        when(accountQuery.list()).thenReturn(accounts);
        List<Account> result = repository.getAccounts();
        assertIterableEquals(accounts, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(Account.class));
        verify(accountQuery, times(1)).list();
    }

    @Test
    void shouldFindAccounts() {
        String firstName = "firstName";
        FindAccountDto findAccountDto = new FindAccountDto();
        findAccountDto.setFirstName(firstName);
        List<Account> accounts = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Account.class))).thenReturn(accountQuery);
        when(accountQuery.setParameter("firstName", "%" + firstName + "%")).thenReturn(accountQuery);
        when(accountQuery.list()).thenReturn(accounts);
        List<Account> result = repository.findAccounts(findAccountDto);
        assertIterableEquals(accounts, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(Account.class));
        verify(accountQuery, times(1)).setParameter("firstName", "%" + firstName + "%");
        verify(accountQuery, times(1)).list();
    }

    @Test
    void shouldGetPosts() {
        List<Post> posts = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Post.class))).thenReturn(postQuery);
        when(postQuery.setParameter(anyString(), eq(account))).thenReturn(postQuery);
        when(postQuery.list()).thenReturn(posts);
        List<Post> result = repository.getPosts(account);
        assertIterableEquals(posts, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(Post.class));
        verify(postQuery, times(1)).setParameter(anyString(), eq(account));
        verify(postQuery, times(1)).list();
    }

    @Test
    void shouldGetDialogs() {
        List<Dialog> dialogs = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Dialog.class))).thenReturn(dialogQuery);
        when(dialogQuery.setParameter(anyString(), eq(account))).thenReturn(dialogQuery);
        when(dialogQuery.list()).thenReturn(dialogs);
        List<Dialog> result = repository.getDialogs(account);
        assertIterableEquals(dialogs, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(Dialog.class));
        verify(dialogQuery, times(1)).setParameter(anyString(), eq(account));
        verify(dialogQuery, times(1)).list();
    }

    @Test
    void shouldGetFriends() {
        List<Account> friends = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Account.class))).thenReturn(accountQuery);
        when(accountQuery.setParameter(anyString(), eq(account))).thenReturn(accountQuery);
        when(accountQuery.list()).thenReturn(friends);
        List<Account> result = repository.getFriends(account);
        assertIterableEquals(friends, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(Account.class));
        verify(accountQuery, times(1)).setParameter(anyString(), eq(account));
        verify(accountQuery, times(1)).list();
    }

    @Test
    void shouldGetFriendInvites() {
        List<FriendInvite> friendInvites = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(FriendInvite.class))).thenReturn(friendInviteQuery);
        when(friendInviteQuery.setParameter(anyString(), eq(account))).thenReturn(friendInviteQuery);
        when(friendInviteQuery.list()).thenReturn(friendInvites);
        List<FriendInvite> result = repository.getFriendInvites(account);
        assertIterableEquals(friendInvites, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(FriendInvite.class));
        verify(friendInviteQuery, times(1)).setParameter(anyString(), eq(account));
        verify(friendInviteQuery, times(1)).list();
    }

    @Test
    void shouldGetGroups() {
        List<Group> groups = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(Group.class))).thenReturn(groupQuery);
        when(groupQuery.setParameter(anyString(), eq(account))).thenReturn(groupQuery);
        when(groupQuery.list()).thenReturn(groups);
        List<Group> result = repository.getGroups(account);
        assertIterableEquals(groups, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(Group.class));
        verify(groupQuery, times(1)).setParameter(anyString(), eq(account));
        verify(groupQuery, times(1)).list();
    }

    @Test
    void shouldGetGroupInvites() {
        List<GroupInvite> groupInvites = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(GroupInvite.class))).thenReturn(groupInviteQuery);
        when(groupInviteQuery.setParameter(anyString(), eq(account))).thenReturn(groupInviteQuery);
        when(groupInviteQuery.setParameter(anyString(), eq(GroupRole.Creator))).thenReturn(groupInviteQuery);
        when(groupInviteQuery.list()).thenReturn(groupInvites);
        List<GroupInvite> result = repository.getGroupInvites(account);
        assertIterableEquals(groupInvites, result);
        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(anyString(), eq(GroupInvite.class));
        verify(groupInviteQuery, times(1)).setParameter(anyString(), eq(account));
        verify(groupInviteQuery, times(1)).setParameter(anyString(), eq(GroupRole.Creator));
        verify(groupInviteQuery, times(1)).list();
    }
}
