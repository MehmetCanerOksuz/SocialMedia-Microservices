package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j //loglama kütüphanesi..
public class RegisterConsumer {

    private final UserService userService;

    @RabbitListener(queues = ("${rabbitmq.register-queue}"))
    public void newUserCreate(RegisterModel model){
        log.info("User {}", model);
        userService.createNewUserWithRabbitmq(model);
    }
}
