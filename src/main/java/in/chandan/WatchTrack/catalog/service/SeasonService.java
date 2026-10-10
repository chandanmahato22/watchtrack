package in.chandan.WatchTrack.catalog.service;

import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.catalog.entity.Season;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.EpisodeRepository;
import in.chandan.WatchTrack.catalog.repository.SeasonRepository;
import in.chandan.WatchTrack.tmdb.dto.TmdbEpisodeDetails;
import in.chandan.WatchTrack.tmdb.dto.TmdbSeasonDetails;
import in.chandan.WatchTrack.tmdb.service.TmdbClient;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SeasonService {

    private final TmdbClient tmdbClient;
    private final TitleService titleService;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;

    public SeasonService(TmdbClient tmdbClient, TitleService titleService, SeasonRepository seasonRepository, EpisodeRepository episodeRepository) {
        this.tmdbClient = tmdbClient;
        this.titleService = titleService;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
    }

    public Season syncSeason(Long tvId, Integer seasonNumber){
        Title title = titleService.saveTv(tvId);

        TmdbSeasonDetails details = tmdbClient.getSeasonDetails(tvId,seasonNumber);

        Season season = seasonRepository.findByTitleAndSeasonNumber(title,
                seasonNumber).orElseGet(Season:: new);

        season.setTitle(title);
        season.setSeasonNumber(seasonNumber);
        season.setName(details.getName());
        season.setAirDate(parseDate(details.getAirDate()));

        seasonRepository.save(season);

        if(details.getEpisodes() != null ){
            for(TmdbEpisodeDetails item : details.getEpisodes()){

                Episode episode = episodeRepository.findBySeasonAndEpisodeNumber(
                        season, item.getEpisodeNumber()
                ).orElseGet(Episode::new);

                episode.setEpisodeNumber(item.getEpisodeNumber());
                episode.setName(item.getName());
                episode.setOverview(item.getOverview());
                episode.setStillPath(item.getStillPath());
                episode.setSeason(season);
                episode.setAirDate(parseDate(item.getAirDate()));
                episodeRepository.save(episode);
            }
        }
        return season;
    }
    private LocalDate parseDate(String date){
        if(date == null || date.isBlank()) return null;

        return LocalDate.parse(date);
    }
}
