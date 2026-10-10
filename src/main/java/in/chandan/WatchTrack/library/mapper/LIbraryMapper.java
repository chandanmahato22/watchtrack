package in.chandan.WatchTrack.library.mapper;

import in.chandan.WatchTrack.enums.TitleType;
import in.chandan.WatchTrack.library.dto.LibraryData;
import in.chandan.WatchTrack.library.entity.UserLibrary;

import java.util.List;

public class LIbraryMapper {

    public static LibraryData toLibraryData(UserLibrary req){

        LibraryData data = new LibraryData();
        data.setStatus(req.getStatus());
        data.setName(req.getTitle().getName());
        data.setMediaType(String.valueOf(req.getTitle().getType()));
        data.setCurrentEpisode(req.getCurrentEpisode());
        data.setCurrentSeason(req.getCurrentSeason());
        data.setId(req.getId());
        return data;
    }
}
