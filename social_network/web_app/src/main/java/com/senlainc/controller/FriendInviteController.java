package com.senlainc.controller;

import com.senlainc.dto.CreateFriendInviteDto;
import com.senlainc.dto.FriendInviteDto;
import com.senlainc.service.FriendInviteService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/friend/invite")
@RestController
public class FriendInviteController {
    private static final Logger log = LogManager.getLogger(FriendInviteController.class);
    private final FriendInviteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendInviteDto create(@RequestBody CreateFriendInviteDto createFriendInviteDto) {
        log.info("Request: 'create', params: {}", createFriendInviteDto);
        return service.create(createFriendInviteDto);
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
