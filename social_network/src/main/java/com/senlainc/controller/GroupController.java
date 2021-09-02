package com.senlainc.controller;

import com.senlainc.dto.CreateGroupDto;
import com.senlainc.dto.GroupChatDto;
import com.senlainc.dto.GroupDto;
import com.senlainc.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/group")
@RestController
public class GroupController {
    private static final Logger log = LogManager.getLogger(GroupController.class);
    private final GroupService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto create(@RequestBody CreateGroupDto createGroupDto) {
        log.info("Request: 'create', params: {}", createGroupDto);
        return service.create(createGroupDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDto update(@RequestBody GroupDto groupDto) {
        log.info("Request: 'update', params: {}", groupDto);
        return service.update(groupDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDto> getGroups() {
        log.info("Request: 'getGroups'");
        return service.getGroups();
    }

    @GetMapping(value = "/{id}/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupChatDto> getChats(@PathVariable long id) {
        log.info("Request: 'getChats', params: {}", id);
        return service.getChats(id);
    }
}
