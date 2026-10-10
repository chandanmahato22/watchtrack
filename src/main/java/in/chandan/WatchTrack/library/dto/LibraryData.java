package in.chandan.WatchTrack.library.dto;

import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.enums.LibraryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryData {
    private Long id;
    private String name;
    private String mediaType;
    private LibraryStatus status;
    private Integer currentEpisode;
    private Integer currentSeason;
}
