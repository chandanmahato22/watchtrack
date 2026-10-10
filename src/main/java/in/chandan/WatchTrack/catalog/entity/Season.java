package in.chandan.WatchTrack.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Documented;
import java.time.LocalDate;

@Entity
@Table(name = "seasons",
            uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_title_season_number",
                columnNames = {"title_id","season_number"}
        )
            })
@Getter
@Setter
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id")
    private Title title;

    @Column(nullable = false)
    private String name;

    @Column(name = "season_number",nullable = false)
    private Integer seasonNumber;

    private LocalDate airDate;

}
