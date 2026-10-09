package in.chandan.WatchTrack.catalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TitleSearchResponse {

    private int page;
    private int totalPage;
    private int totalResult;
    private List<TitleSearchResult> results;
}
