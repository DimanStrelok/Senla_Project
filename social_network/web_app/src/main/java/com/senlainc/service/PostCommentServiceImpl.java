package com.senlainc.service;

import com.senlainc.dto.CreatePostCommentDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.entity.Account;
import com.senlainc.entity.Post;
import com.senlainc.entity.PostComment;
import com.senlainc.mapper.PostCommentMapper;
import com.senlainc.repository.AccountRepository;
import com.senlainc.repository.PostCommentRepository;
import com.senlainc.repository.PostRepository;
import com.senlainc.security.AuthenticationAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostCommentServiceImpl implements PostCommentService {
    private final PostCommentRepository repository;
    private final PostCommentMapper mapper;
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    private final AuthenticationAccess authenticationAccess;

    @Transactional
    @Override
    public PostCommentDto create(CreatePostCommentDto createPostCommentDto) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        Post post = postRepository.get(createPostCommentDto.getPostId());
        Account account = accountRepository.get(createPostCommentDto.getAccountId());
        if (authenticatedAccount.getId() != account.getId()) {
            throw new AccessDeniedException("access to create post comment from account " + account + " via account " + authenticatedAccount + " denied");
        }
        LocalDateTime now = LocalDateTime.now();
        PostComment entity = new PostComment();
        entity.setPost(post);
        entity.setAccount(account);
        entity.setText(createPostCommentDto.getText());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        repository.create(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public PostCommentDto get(long id) {
        return mapper.entityToDto(repository.get(id));
    }

    @Transactional
    @Override
    public PostCommentDto changeText(long id, String text) {
        Account authenticatedAccount = authenticationAccess.getAuthenticatedAccount();
        PostComment entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId()) {
            throw new AccessDeniedException("access to update post comment " + entity + " via account " + authenticatedAccount + " denied");
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
        PostComment entity = repository.get(id);
        if (authenticatedAccount.getId() != entity.getAccount().getId()) {
            throw new AccessDeniedException("access to delete post comment " + entity + " via account " + authenticatedAccount + " denied");
        }
        repository.delete(entity);
    }
}
