package in.chandan.WatchTrack.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "episodes",
                uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_season_episode_number",
                columnNames = {"season_id","episode_number"}
        )
                })
@Getter
@Setter
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id",nullable = false)
    private Season season;

    @Column(name = "episode_number",nullable = false)
    private Integer episodeNumber;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private String stillPath;

    private Double rating;

    private LocalDate airDate;

}
