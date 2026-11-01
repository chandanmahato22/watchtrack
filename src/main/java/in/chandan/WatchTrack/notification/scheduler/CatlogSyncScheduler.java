package in.chandan.WatchTrack.notification.scheduler;

import in.chandan.WatchTrack.catalog.service.CatlogSyncService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CatlogSyncScheduler {

    private final CatlogSyncService catlogSyncService;

    public CatlogSyncScheduler(CatlogSyncService catlogSyncService) {
        this.catlogSyncService = catlogSyncService;
    }
    @Scheduled(
            fixedDelay = 1,
            timeUnit =  TimeUnit.HOURS,
            initialDelay = 1
    )
    public void syncCatlog(){
        catlogSyncService.syncAllTvShows();
    }
}
