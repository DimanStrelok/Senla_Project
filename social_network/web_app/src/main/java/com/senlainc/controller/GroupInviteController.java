package com.senlainc.controller;

import com.senlainc.dto.CreateGroupInviteDto;
import com.senlainc.dto.GroupInviteDto;
import com.senlainc.service.GroupInviteService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/group/invite")
@RestController
public class GroupInviteController {
    private static final Logger log = LogManager.getLogger(GroupInviteController.class);
    private final GroupInviteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupInviteDto create(@RequestBody CreateGroupInviteDto createGroupInviteDto) {
        log.info("Request: 'create', params: {}", createGroupInviteDto);
        return service.create(createGroupInviteDto);
    }

    @PutMapping("/{id}/accept")
    public void acceptInvite(@PathVariable long id) {
        log.info("Request: 'acceptInvite', params: {}", id);
        service.acceptInvite(id);
    }

    @PutMapping("/{id}/reject")
    public void rejectInvite(@PathVariable long id) {
        log.info("Request: 'rejectInvite', params: {}", id);
        service.rejectInvite(id);
    }
}
