package in.chandan.WatchTrack.notification.service;

import in.chandan.WatchTrack.notification.dto.EmailNotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationConsumer {

    private final EmailService emailService;

    public EmailNotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${watchtrack.rabbitmq.queue}")
    public void receiveEmailNotification(EmailNotificationMessage message){
        emailService.sendMail(message.recipient(),
                message.subject(),
                message.body());
    }
}
