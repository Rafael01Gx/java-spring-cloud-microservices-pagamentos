package com.food.pagamentos.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoAMQPConfig {

    public static final String PAGAMENTO_CONCLUIDO_QUEUE = "pagamento.concluido";

    @Bean
    public Queue pagamentoQueue() {

        return QueueBuilder
                .durable(PAGAMENTO_CONCLUIDO_QUEUE)
                .build();
    }

    @Bean
    public JacksonJsonMessageConverter rabbitMessageConverter() {
        return  new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, JacksonJsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

}
