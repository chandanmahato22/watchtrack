package in.chandan.WatchTrack.library.dto;

import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.enums.LibraryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryData {
    private Long id;
    private Title title;
    private LibraryStatus status;
    private Integer currentEpisode;
    private Integer currentSeason;
}
