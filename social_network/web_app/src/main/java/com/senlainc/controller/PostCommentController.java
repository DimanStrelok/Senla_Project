package com.senlainc.controller;

import com.senlainc.dto.CreatePostCommentDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/post/comment")
@RestController
public class PostCommentController {
    private static final Logger log = LogManager.getLogger(PostCommentController.class);
    private final PostCommentService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostCommentDto create(@RequestBody CreatePostCommentDto createPostCommentDto) {
        log.info("Request: 'create', params: {}", createPostCommentDto);
        return service.create(createPostCommentDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostCommentDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostCommentDto changeText(@PathVariable long id, @RequestBody String text) {
        log.info("Request: 'changeText', params: {}, {}", id, text);
        return service.changeText(id, text);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }
}
