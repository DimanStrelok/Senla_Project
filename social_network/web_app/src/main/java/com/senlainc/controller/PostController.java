package com.senlainc.controller;

import com.senlainc.dto.CreatePostDto;
import com.senlainc.dto.PostCommentDto;
import com.senlainc.dto.PostDto;
import com.senlainc.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {
    private static final Logger log = LogManager.getLogger(PostController.class);
    private final PostService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto create(@RequestBody CreatePostDto createPostDto) {
        log.info("Request: 'create', params: {}", createPostDto);
        return service.create(createPostDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto changeText(@PathVariable long id, @RequestBody String text) {
        log.info("Request: 'changeText', params: {}, {}", id, text);
        return service.changeText(id, text);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }

    @GetMapping(value = "/{id}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostCommentDto> getComments(@PathVariable long id) {
        log.info("Request: 'getComments', params: {}", id);
        return service.getComments(id);
    }
}
