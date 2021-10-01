package com.senlainc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "#{queue}")
@Service
public class PrintMessageService {
    private static final Logger log = LogManager.getLogger(PrintMessageService.class);

    @RabbitHandler
    public void receiveMessage(String message) {
        log.info("Receive Message - " + message);
    }
}
