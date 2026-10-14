package in.chandan.WatchTrack.notification.scheduler;


import in.chandan.WatchTrack.catalog.service.EpisodeTrackingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EpisodeNotificationScheduler {

    private final EpisodeTrackingService episodeTrackingService;


    public EpisodeNotificationScheduler(EpisodeTrackingService episodeTrackingService) {
        this.episodeTrackingService = episodeTrackingService;
    }
    @Scheduled(cron = "5 * * * * * ", zone = "Asia/Kolkata")
    public void checkEpisodeAiringToday(){
        int created = episodeTrackingService.createNotificationsForAiredEpisode();
        System.out.println("episode notification check completed. created: " + created);
    }
}
