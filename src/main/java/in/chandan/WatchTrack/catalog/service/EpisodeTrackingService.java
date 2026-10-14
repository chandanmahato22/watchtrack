package in.chandan.WatchTrack.catalog.service;

import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.EpisodeRepository;
import in.chandan.WatchTrack.enums.LibraryStatus;
import in.chandan.WatchTrack.library.entity.UserLibrary;
import in.chandan.WatchTrack.library.repository.LibraryRepository;
import in.chandan.WatchTrack.library.service.LibraryService;
import in.chandan.WatchTrack.notification.entity.Notification;
import in.chandan.WatchTrack.notification.repository.NotificationRepository;
import in.chandan.WatchTrack.users.entity.User;
import org.aspectj.weaver.ast.Not;
import org.hibernate.tool.schema.internal.exec.ScriptSourceInputFromReader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EpisodeTrackingService {

    private final EpisodeRepository episodeRepository;
    private final LibraryRepository libraryRepository;
    private final NotificationRepository notificationRepository;

    public EpisodeTrackingService(EpisodeRepository episodeRepository, LibraryRepository libraryRepository, NotificationRepository notificationRepository) {
        this.episodeRepository = episodeRepository;
        this.libraryRepository = libraryRepository;
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public List<Episode> findAiredEpisodes(){
        return episodeRepository.findByAirDate(LocalDate.now());
    }

    public List<UserLibrary> findByEligibleUsers(Episode episode){
        Title title = episode.getSeason().getTitle();

        return libraryRepository.findByTitleAndStatusIn(title,
                List.of(
                        LibraryStatus.WATCHING,
                        LibraryStatus.PLAN_TO_WATCH
                ));
    }
    @Transactional
    public int createNotificationsForAiredEpisode(){
        int created = 0;

        List<Episode> episodes = findAiredEpisodes();

        for(Episode episode : episodes){
            List<UserLibrary> eligibleEntries = findByEligibleUsers(episode);

            for(UserLibrary entry : eligibleEntries){

                if(notificationRepository.existsByUserAndEpisode(entry.getUser(),episode)){
                    continue;
                }

                Notification notification = new Notification();

                notification.setUser(entry.getUser());
                notification.setEpisode(episode);
                notification.setMsg(entry.getTitle().getName() + " new episode number " + episode.getEpisodeNumber() +
                                                        " willing be airing today don't forget to watch today");
                notificationRepository.save(notification);
                created++;
            }
        }
        return created;
    }
}
