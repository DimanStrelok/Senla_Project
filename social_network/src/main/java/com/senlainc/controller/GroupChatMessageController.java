package com.senlainc.controller;

import com.senlainc.dto.CreateGroupChatMessageDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.service.GroupChatMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/group/chat/message")
@RestController
public class GroupChatMessageController {
    private static final Logger log = LogManager.getLogger(GroupChatMessageController.class);
    private final GroupChatMessageService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupChatMessageDto create(@RequestBody CreateGroupChatMessageDto createChatCommentDto) {
        log.info("Request: 'create', params: {}", createChatCommentDto);
        return service.create(createChatCommentDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupChatMessageDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupChatMessageDto changeText(@PathVariable long id, @RequestBody String text) {
        log.info("Request: 'changeText', params: {}, {}", id, text);
        return service.changeText(id, text);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }
}
