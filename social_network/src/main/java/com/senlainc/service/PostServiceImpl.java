package com.senlainc.service;

import com.senlainc.dto.CreatePostDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.dto.PostDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Post;
import com.senlainc.mapper.PostCommentMapper;
import com.senlainc.mapper.PostMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.PostRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper mapper;
    private final PostCommentMapper commentMapper;
    private final AccountRepository accountRepository;
    private final AuthenticationAccess authenticationAccess;

    @Transactional
    @Override
    public PostDto create(CreatePostDto createPostDto) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        Account account = accountRepository.get(createPostDto.getAccountId());
        if (authenticatedAccount.getId() != account.getId()) {
            throw new AccessDeniedException("access to create post from account " + account + " via account " + authenticatedAccount + " denied");
        }
        LocalDateTime now = LocalDateTime.now();
        Post entity = new Post();
        entity.setAccount(account);
        entity.setText(createPostDto.getText());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto get(long id) {
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public PostDto changeText(long id, String text) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        Post entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId()) {
            throw new AccessDeniedException("access to update post " + entity + " via account " + authenticatedAccount + " denied");
        }
        entity.setText(text);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        Post entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId()) {
            throw new AccessDeniedException("access to delete post " + entity + " via account " + authenticatedAccount + " denied");
        }
        repository.delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostCommentDto> getComments(long id) {
        Post post = repository.get(id);
        return commentMapper.entityListToDtoList(repository.getComments(post));
    }
}
