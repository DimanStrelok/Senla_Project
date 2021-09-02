package com.senlainc.controller;

import com.senlainc.dto.CreateGroupChatDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupChatMessageDto;
import com.senlainc.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/group/chat")
@RestController
public class GroupChatController {
    private static final Logger log = LogManager.getLogger(GroupChatController.class);
    private final GroupChatService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupChatDto create(@RequestBody CreateGroupChatDto createGroupChatDto) {
        log.info("Request: 'create', params: {}", createGroupChatDto);
        return service.create(createGroupChatDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupChatDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupChatDto changeTitle(@PathVariable long id, @RequestBody String text) {
        log.info("Request: 'changeTitle', params: {}, {}", id, text);
        return service.changeTitle(id, text);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }

    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupChatMessageDto> getMessages(@PathVariable long id) {
        log.info("Request: 'getMessages', params: {}", id);
        return service.getMessages(id);
    }
}
