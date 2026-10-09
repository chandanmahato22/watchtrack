package in.chandan.WatchTrack.catalog.dto;


import in.chandan.WatchTrack.enums.TitleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TitleSearchResult {

    private Long externalId;
    private String title;

    private TitleType type;

    private String overview;
    private String posterPath;
    private String backdropPath;
    private String language;
    private String releaseDate;
    private Double rating;
    private Integer voteCount;
}
