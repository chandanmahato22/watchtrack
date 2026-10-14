package in.chandan.WatchTrack.notification.repository;

import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.notification.entity.Notification;
import in.chandan.WatchTrack.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    boolean existsByUserAndEpisode(User user, Episode episode);

    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
