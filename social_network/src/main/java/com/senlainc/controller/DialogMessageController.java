package com.senlainc.controller;

import com.senlainc.dto.CreateDialogMessageDto;
import com.senlainc.dto.DialogMessageDto;
import com.senlainc.service.DialogMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/dialog/message")
@RestController
public class DialogMessageController {
    private static final Logger log = LogManager.getLogger(DialogMessageController.class);
    private final DialogMessageService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DialogMessageDto create(@RequestBody CreateDialogMessageDto createDialogMessageDto) {
        log.info("Request: 'create', params: {}", createDialogMessageDto);
        return service.create(createDialogMessageDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DialogMessageDto get(@PathVariable long id) {
        log.info("Request: 'get', params: {}", id);
        return service.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DialogMessageDto changeText(@PathVariable long id, @RequestBody String text) {
        log.info("Request: 'changeText', params: {}, {}", id, text);
        return service.changeText(id, text);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("Request: 'delete', params: {}", id);
        service.delete(id);
    }
}
