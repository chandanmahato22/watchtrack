package in.chandan.WatchTrack.catalog.mapper;

import in.chandan.WatchTrack.catalog.dto.TitleSearchResult;
import in.chandan.WatchTrack.catalog.dto.TmdbMovieDetails;
import in.chandan.WatchTrack.catalog.dto.TmdbTvDetails;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.enums.TitleType;
import in.chandan.WatchTrack.tmdb.dto.TmdbSearchResult;

import java.time.LocalDate;

public class TittleMapper {

    public static TitleSearchResult toTitleSearchResult(TmdbSearchResult tmdb){
        TitleSearchResult  result = new TitleSearchResult();
        result.setExternalId(tmdb.getId());

        if(tmdb.getTitle() != null){
            result.setTitle(tmdb.getTitle());
        }
        else {
            result.setTitle(tmdb.getName());
        }

        result.setOverview(tmdb.getOverview());
        result.setPosterPath(tmdb.getPosterPath());
        result.setBackdropPath(tmdb.getBackdropPath());
        result.setLanguage(tmdb.getOriginalLanguage());
        result.setRating(tmdb.getVoteAverage());
        result.setVoteCount(tmdb.getVoteCount());

        if(tmdb.getReleaseDate() != null && !tmdb.getReleaseDate().isBlank()){
            result.setType(TitleType.MOVIE);
            result.setReleaseDate(tmdb.getReleaseDate());
        }
        else{
            result.setReleaseDate(tmdb.getFirstAirDate());
            result.setType(TitleType.TV);
        }

        return result;
    }
    public static Title toTitle(TmdbMovieDetails tmdb){
        Title title = new Title();

        title.setExternalId(tmdb.getId());
        title.setProvider(tmdb.getProvider());
        title.setName(tmdb.getTitle());
        title.setBackDropPath(tmdb.getBackdropPath());
        title.setLanguage(tmdb.getOriginalLanguage());
        title.setType(TitleType.MOVIE);
        title.setOverview(tmdb.getOverview());
        title.setPosterPath(tmdb.getPosterPath());
        title.setVoteCount(tmdb.getVoteCount());
        title.setRating(tmdb.getVoteAverage());

        if(tmdb.getReleaseDate() != null && !tmdb.getReleaseDate().isBlank()){
            title.setReleaseDate(LocalDate.parse(tmdb.getReleaseDate()));
        }

        return title;
    }
    public static Title toTitle(TmdbTvDetails tmdb){
        Title title = new Title();

        title.setExternalId(tmdb.getId());
        title.setProvider(tmdb.getProvider());
        title.setName(tmdb.getName());
        title.setBackDropPath(tmdb.getBackdropPath());
        title.setLanguage(tmdb.getOriginalLanguage());
        title.setType(TitleType.TV);
        title.setOverview(tmdb.getOverview());
        title.setPosterPath(tmdb.getPosterPath());
        title.setVoteCount(tmdb.getVoteCount());
        title.setRating(tmdb.getVoteAverage());

        if(tmdb.getReleaseDate() != null && !tmdb.getReleaseDate().isBlank()){
            title.setReleaseDate(LocalDate.parse(tmdb.getReleaseDate()));
        }

        return title;
    }
}
