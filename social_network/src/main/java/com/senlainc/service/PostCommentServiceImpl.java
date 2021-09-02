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
import lombok.RequiredArgsConstructor;
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

    @Transactional
    @Override
    public PostCommentDto create(CreatePostCommentDto createPostCommentDto) {
        Post post = postRepository.get(createPostCommentDto.getPostId());
        Account account = accountRepository.get(createPostCommentDto.getAccountId());
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
        PostComment entity = repository.get(id);
        entity.setText(text);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.update(entity);
        return mapper.entityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        repository.delete(repository.get(id));
    }
}
