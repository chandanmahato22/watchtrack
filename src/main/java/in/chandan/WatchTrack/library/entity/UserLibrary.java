package in.chandan.WatchTrack.library.entity;

import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.enums.LibraryStatus;
import in.chandan.WatchTrack.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "user_library",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_title",
                        columnNames = {"user_id","title_id"}
                )
        }
)
public class UserLibrary {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id",nullable = false)
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LibraryStatus status;

    private Integer currentSeason;
    private Integer currentEpisode;
}
