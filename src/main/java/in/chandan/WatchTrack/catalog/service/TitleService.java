package in.chandan.WatchTrack.catalog.service;

import in.chandan.WatchTrack.catalog.dto.TitleSearchResponse;
import in.chandan.WatchTrack.catalog.dto.TitleSearchResult;
import in.chandan.WatchTrack.catalog.dto.TmdbMovieDetails;
import in.chandan.WatchTrack.catalog.dto.TmdbTvDetails;
import in.chandan.WatchTrack.enums.Provider;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.TitleRepository;
import in.chandan.WatchTrack.catalog.mapper.TittleMapper;
import in.chandan.WatchTrack.tmdb.service.TmdbClient;
import in.chandan.WatchTrack.tmdb.dto.TmdbSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {

    private TmdbClient tmdbClient;
    private TitleRepository titleRepository;
    public TitleService(TmdbClient tmdbClient,TitleRepository titleRepository){
        this.tmdbClient = tmdbClient;
        this.titleRepository = titleRepository;
    }

    public TitleSearchResponse searchTitles(String query){

        TmdbSearchResponse tmdbResponse = tmdbClient.search(query);

        List<TitleSearchResult> results = tmdbResponse.getResults()
                .stream()
                .map(TittleMapper::toTitleSearchResult)
                .toList();

        TitleSearchResponse response = new TitleSearchResponse();
        response.setPage(tmdbResponse.getPage());
        response.setTotalPage(tmdbResponse.getTotalPages());
        response.setTotalResult(tmdbResponse.getTotalResults());
        response.setResults(results);

        return response;
    }

    public Title saveMovie(Long id){
        return titleRepository.findByExternalIdAndProvider(id,Provider.TMDB).orElseGet(
                () -> {
                    TmdbMovieDetails tmdb = tmdbClient.getMovieDetails(id);
                    Title title = TittleMapper.toTitle(tmdb);
                    return titleRepository.save(title);
                }
        );
    }

    public Title saveTv(Long id){
        return titleRepository.findByExternalIdAndProvider(id,Provider.TMDB).orElseGet(
                () -> {
                    TmdbTvDetails tmdb = tmdbClient.getTvDetails(id);
                    Title title = TittleMapper.toTitle(tmdb);
                    return titleRepository.save(title);
                }
        );
    }
}
