package com.socialmedia.config.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Value("${rabbitmq.activation-queue}")
    private String activationQueueName;


    @Value("${rabbitmq.register-elastic-queue}")
    private String registerElasticQueueName;
    @Value("${rabbitmq.register-elastic-binding}")
    private String registerElasticBinding;

    @Value("${rabbitmq.user-exchange}")
    private String exchange;

    @Bean
    public Queue registerQueue(){
        return new Queue(registerQueueName);
    }

    @Bean
    public Queue activationQueue() {
        return new Queue(activationQueueName);
    }

    @Bean
    public Queue registerElasticQueue() {
        return new Queue(registerElasticQueueName);
    }

    @Bean
    public DirectExchange userExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding bindingRegisterElastic(final Queue registerElasticQueue,final DirectExchange userExchange){
        return BindingBuilder.bind(registerElasticQueue).to(userExchange).with(registerElasticBinding);
    }

}
