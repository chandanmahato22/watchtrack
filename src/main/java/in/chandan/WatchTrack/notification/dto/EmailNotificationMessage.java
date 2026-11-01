package in.chandan.WatchTrack.notification.dto;

public record EmailNotificationMessage(
        String recipient,
        String subject,
        String body
){}
