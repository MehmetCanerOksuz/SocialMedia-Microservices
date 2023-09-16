package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.mapper.IElasticMapper;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterElasticConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.register-elastic-queue}")
    public void newUserCreate(RegisterElasticModel model) {
        log.info("Model ==>> {}",model);
        userService.save(IElasticMapper.INSTANCE.toUserProfile(model));
    }
}
