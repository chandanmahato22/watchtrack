package in.chandan.WatchTrack.notification.service;

import in.chandan.WatchTrack.notification.dto.EmailNotificationMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${watchtrack.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${watchtrack.rabbitmq.routing-key}")
    private String routingKey;

    public EmailNotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publish(EmailNotificationMessage message){
        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                message
        );
        System.out.println("email notification publisher send : " + message.recipient());
    }

}
