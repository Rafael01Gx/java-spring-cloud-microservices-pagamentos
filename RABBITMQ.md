# RabbitMQ com Spring Boot 4 e Java 25

Este documento descreve, de forma **moderna e prática**, como configurar e utilizar **RabbitMQ** em aplicações **Spring Boot 4** com **Java 25**, seguindo boas práticas de arquitetura, observabilidade e desacoplamento.

---

## 📌 Visão Geral

O RabbitMQ é um **message broker** amplamente utilizado para comunicação assíncrona entre sistemas. No ecossistema Spring, ele é integrado via **Spring AMQP**, oferecendo abstrações de alto nível para publicação e consumo de mensagens.

### Benefícios principais

* Comunicação assíncrona e desacoplada
* Escalabilidade horizontal
* Tolerância a falhas
* Suporte a múltiplos padrões (pub/sub, work queues, routing)

---

## 🧱 Dependências (Maven)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

Compatível com:

* **Spring Boot 4.x**
* **Java 25**

---

## ⚙️ Configuração do application.yaml

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    connection-timeout: 5s

    listener:
      simple:
        concurrency: 3
        max-concurrency: 10
        prefetch: 10
        default-requeue-rejected: false
        retry:
          enabled: true
          initial-interval: 1s
          max-attempts: 3
          multiplier: 2

  jackson:
    serialization:
      write-dates-as-timestamps: false
```

### Destaques

* **prefetch**: controla quantas mensagens o consumer pode processar simultaneamente
* **retry**: política de retentativa automática
* **default-requeue-rejected**: evita loops infinitos de mensagens com erro

---

## 🧩 Arquitetura de Mensageria

Padrão recomendado:

```
Producer → Exchange → Queue → Consumer
```

* **Exchange**: define a regra de roteamento
* **Queue**: armazena as mensagens
* **Binding**: liga Exchange e Queue

---

## 🛠️ Classe de Configuração (@Configuration)

```java
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "orders.exchange";
    public static final String QUEUE_NAME = "orders.queue";
    public static final String ROUTING_KEY = "orders.created";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "orders.dlx")
                .build();
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}
```

### Boas práticas aplicadas

* Exchange e Queue **duráveis**
* Uso de **Dead Letter Exchange (DLX)**
* Separação clara de responsabilidades

---

## ☠️ Dead Letter Queue (DLQ)

```java
@Bean
public TopicExchange deadLetterExchange() {
    return new TopicExchange("orders.dlx");
}

@Bean
public Queue deadLetterQueue() {
    return QueueBuilder.durable("orders.dlq").build();
}

@Bean
public Binding dlqBinding() {
    return BindingBuilder
            .bind(deadLetterQueue())
            .to(deadLetterExchange())
            .with("#");
}
```

Utilizada para:

* Mensagens com erro
* Timeout
* Exceções não tratadas

---

## 📤 Producer (Publicador de Mensagens)

```java
@Service
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}
```

### Observações

* `convertAndSend` usa **Jackson** automaticamente
* Ideal para eventos de domínio

---

## 📥 Consumer (Listener)

```java
@Component
public class OrderConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consume(OrderCreatedEvent event) {
        // processamento de negócio
        System.out.println("Pedido recebido: " + event.id());
    }
}
```

### Boas práticas

* Consumers **idempotentes**
* Lógica de negócio isolada
* Evitar transações longas

---

## 🔄 Tratamento de Erros e Retry

```java
@Bean
public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
        ConnectionFactory connectionFactory
) {
    var factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setDefaultRequeueRejected(false);
    return factory;
}
```

Erros não tratados:

* Mensagem → DLQ
* Evita reprocessamento infinito

---

## 🔍 Observabilidade

Recomendações:

* Logs estruturados
* Correlation ID nas mensagens
* Integração com Prometheus + Grafana
* RabbitMQ Management Plugin

---

## 🧪 Testes

### Testes de Integração

* Testcontainers (RabbitMQ)
* @SpringBootTest

### Testes de Contrato

* Verificar payload
* Garantir compatibilidade entre serviços

---

## 🏁 Conclusão

O uso de RabbitMQ com **Spring Boot 4** e **Java 25** permite construir sistemas:

* Escaláveis
* Resilientes
* Orientados a eventos

Seguindo essas práticas, você garante uma infraestrutura de mensageria **robusta, moderna e pronta para produção**.

---

📚 Referências

* Spring AMQP
* RabbitMQ Official Docs
* Cloud Native Messaging Patterns
