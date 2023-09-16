package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j //loglama kütüphanesi..
public class ActivationConsumer {

    private final UserService userService;

    @RabbitListener(queues = ("${rabbitmq.activation-queue}"))
    public void activateStatus(String token){
        log.info("token => {}",token);
        userService.activateStatus(token);
    }
}
