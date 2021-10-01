package com.senlainc.controller;

import com.senlainc.dto.DialogDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.service.DialogService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/dialog")
@RestController
public class DialogController {
    private static final Logger log = LogManager.getLogger(DialogController.class);
    private final DialogService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DialogDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DialogMessageDto> getMessages(@PathVariable long id) {
        log.info("Request: 'getMessages', params: {}", id);
        return service.getMessages(id);
    }
}
