package in.chandan.WatchTrack.catalog.service;

import in.chandan.WatchTrack.catalog.entity.Season;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.SeasonRepository;
import in.chandan.WatchTrack.catalog.repository.TitleRepository;
import in.chandan.WatchTrack.enums.TitleType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatlogSyncService {
    private final TitleRepository titleRepository;
    private final SeasonRepository seasonRepository;
    private final SeasonService seasonService;

    public CatlogSyncService(TitleRepository titleRepository, SeasonRepository seasonRepository, SeasonService seasonService) {
        this.titleRepository = titleRepository;
        this.seasonRepository = seasonRepository;
        this.seasonService = seasonService;
    }

    public void syncAllTvShows() {
        List<Title> titles = titleRepository.findByType(TitleType.TV);

        for(Title title : titles){
            Season season = seasonRepository.findFirstByTitleOrderBySeasonNumberDesc(title).orElse(null);
            int seasonNumber =1;

            if(season == null ){
                seasonService.syncSeason(title.getExternalId(),1);
            }else {
                seasonNumber = season.getSeasonNumber();
                for (int i = 1; i < seasonNumber; i++)
                    seasonService.syncSeason(title.getExternalId(), i);
            }

            // check for new season
            int nextSeason = seasonNumber + 1;
            while(true) {
                try {
                    seasonService.syncSeason(title.getExternalId(), nextSeason);
                    nextSeason++;
                } catch (RuntimeException ex) {
                    break;
                }
            }
        }
    }
}
