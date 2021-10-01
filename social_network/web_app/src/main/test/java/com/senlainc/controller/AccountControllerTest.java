package com.senlainc.controller;

import com.senlainc.dto.*;
import com.senlainc.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    long id = 1;
    String firstName = "firstName";
    String lastName = "lastName";
    String middleName = "middleName";
    String email = "email";
    String phoneNumber = "phoneNumber";
    String password = "password";
    String city = "city";
    AccountDto dto = new AccountDto();

    @Mock
    AccountService service;
    AccountController controller;

    @BeforeEach
    void setUp() {
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setMiddleName(middleName);
        dto.setEmail(email);
        dto.setPhoneNumber(phoneNumber);
        dto.setPassword(password);
        dto.setCity(city);
        controller = new AccountController(service);
    }

    @Test
    void shouldCreateAccount() {
        CreateAccountDto createDto = new CreateAccountDto();
        createDto.setFirstName(firstName);
        createDto.setLastName(lastName);
        createDto.setMiddleName(middleName);
        createDto.setEmail(email);
        createDto.setPhoneNumber(phoneNumber);
        createDto.setPassword(password);
        createDto.setCity(city);
        when(service.create(createDto)).thenReturn(dto);
        AccountDto result = controller.create(createDto);
        assertEquals(dto, result);
        verify(service, times(1)).create(createDto);
    }

    @Test
    void shouldGetAccount() {
        when(service.get(id)).thenReturn(dto);
        AccountDto result = controller.get(id);
        assertEquals(dto, result);
        verify(service, times(1)).get(id);
    }

    @Test
    void shouldUpdateAccount() {
        String newFirstName = "newFirstName";
        dto.setFirstName(newFirstName);
        when(service.update(dto)).thenReturn(dto);
        AccountDto result = controller.update(dto);
        assertEquals(dto, result);
        verify(service, times(1)).update(dto);
    }

    @Test
    void shouldDeleteAccount() {
        doNothing().when(service).delete(id);
        controller.delete(id);
        verify(service, times(1)).delete(id);
    }

    @Test
    void shouldGetAccounts() {
        List<AccountDto> accounts = new ArrayList<>();
        accounts.add(dto);
        when(service.getAccounts()).thenReturn(accounts);
        List<AccountDto> result = controller.getAccounts();
        assertIterableEquals(accounts, result);
        verify(service, times(1)).getAccounts();
    }

    @Test
    void shouldFindAccounts() {
        FindAccountDto findAccountDto = new FindAccountDto();
        findAccountDto.setFirstName(firstName);
        List<AccountDto> accounts = new ArrayList<>();
        accounts.add(dto);
        when(service.findAccounts(findAccountDto)).thenReturn(accounts);
        List<AccountDto> result = controller.findAccounts(findAccountDto);
        assertIterableEquals(accounts, result);
        verify(service, times(1)).findAccounts(findAccountDto);
    }

    @Test
    void shouldGetPosts() {
        PostDto post = new PostDto();
        post.setAccountId(id);
        List<PostDto> posts = new ArrayList<>();
        posts.add(post);
        when(service.getPosts(id)).thenReturn(posts);
        List<PostDto> result = controller.getPosts(id);
        assertIterableEquals(posts, result);
        verify(service, times(1)).getPosts(id);
    }

    @Test
    void shouldGetDialogs() {
        DialogDto dialog1 = new DialogDto();
        dialog1.setAccount1Id(id);
        DialogDto dialog2 = new DialogDto();
        dialog2.setAccount2Id(id);
        List<DialogDto> dialogs = new ArrayList<>();
        dialogs.add(dialog1);
        dialogs.add(dialog2);
        when(service.getDialogs(id)).thenReturn(dialogs);
        List<DialogDto> result = controller.getDialogs(id);
        assertIterableEquals(dialogs, result);
        verify(service, times(1)).getDialogs(id);
    }

    @Test
    void shouldGetFriends() {
        AccountDto friend = new AccountDto();
        List<AccountDto> friends = new ArrayList<>();
        friends.add(friend);
        when(service.getFriends(id)).thenReturn(friends);
        List<AccountDto> result = controller.getFriends(id);
        assertIterableEquals(friends, result);
        verify(service, times(1)).getFriends(id);
    }

    @Test
    void shouldGetFriendInvites() {
        FriendInviteDto friendInvite = new FriendInviteDto();
        friendInvite.setToAccountId(id);
        List<FriendInviteDto> friendInvites = new ArrayList<>();
        friendInvites.add(friendInvite);
        when(service.getFriendInvites(id)).thenReturn(friendInvites);
        List<FriendInviteDto> result = controller.getFriendInvites(id);
        assertIterableEquals(friendInvites, result);
        verify(service, times(1)).getFriendInvites(id);
    }

    @Test
    void shouldGetGroups() {
        GroupDto group = new GroupDto();
        List<GroupDto> groups = new ArrayList<>();
        groups.add(group);
        when(service.getGroups(id)).thenReturn(groups);
        List<GroupDto> result = controller.getGroups(id);
        assertIterableEquals(groups, result);
        verify(service, times(1)).getGroups(id);
    }

    @Test
    void shouldGetGroupInvites() {
        GroupInviteDto groupInvite = new GroupInviteDto();
        groupInvite.setAccountId(id);
        List<GroupInviteDto> groupInvites = new ArrayList<>();
        groupInvites.add(groupInvite);
        when(service.getGroupInvites(id)).thenReturn(groupInvites);
        List<GroupInviteDto> result = controller.getGroupInvites(id);
        assertIterableEquals(groupInvites, result);
        verify(service, times(1)).getGroupInvites(id);
    }
}
