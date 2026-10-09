package in.chandan.WatchTrack.catalog.entity;

import in.chandan.WatchTrack.enums.Provider;
import in.chandan.WatchTrack.enums.TitleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "titles",
        uniqueConstraints =  {
                @UniqueConstraint(
                        name = "uk_provider_external_id",
                        columnNames = {"provider","external_id"}
                )
        }
)
@Getter
@Setter
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id",unique = true )
    private Long externalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TitleType type;

    private String posterPath;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private String backDropPath;

    private String language;

    private LocalDate releaseDate;

    private Double rating;

    private Integer voteCount;
}
