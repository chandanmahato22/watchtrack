package in.chandan.WatchTrack.notification.entity;

import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications",
        uniqueConstraints = @UniqueConstraint(
                name ="uk_notification_user_episode",
                columnNames = {"user_id","episode_id"}
        ))
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @Column(nullable = false)
    private String msg;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreated(){
        createdAt = LocalDateTime.now();
    }
}
