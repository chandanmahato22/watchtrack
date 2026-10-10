package in.chandan.WatchTrack.library.dto;

import in.chandan.WatchTrack.enums.LibraryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToLibraryRequest {

    private Long externalId;
    private String status;
    private String type;
    private Integer season = 1;
    private Integer episode = 1;

}
