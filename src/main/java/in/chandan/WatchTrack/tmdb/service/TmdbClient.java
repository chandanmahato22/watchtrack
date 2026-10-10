package in.chandan.WatchTrack.tmdb.service;

import in.chandan.WatchTrack.catalog.dto.TmdbMovieDetails;
import in.chandan.WatchTrack.catalog.dto.TmdbTvDetails;
import in.chandan.WatchTrack.tmdb.dto.TmdbSearchResponse;
import in.chandan.WatchTrack.tmdb.dto.TmdbSeasonDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TmdbClient {

    private final RestClient restClient;

    public TmdbClient(
          @Value("${tmdb.api.base-url}") String baseurl,
          @Value("${tmdb.api.token}") String token
            ){
            this.restClient = RestClient.builder()
                    .baseUrl(baseurl)
                    .defaultHeader(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + token
                    )
                    .defaultHeader(HttpHeaders.ACCEPT,
                            MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }

    public TmdbSearchResponse search(String query){
         return restClient.get()
                 .uri(uriBuilder -> uriBuilder
                         .path("/search/multi")
                         .queryParam("query",query)
                         .build())
                 .retrieve()
                 .body(TmdbSearchResponse.class);
    }

    public TmdbMovieDetails getMovieDetails(Long id){
        return restClient.get()
                .uri("/movie/{id}",id)
                .retrieve()
                .body(TmdbMovieDetails.class);
    }
    public TmdbTvDetails getTvDetails(Long id){

        return restClient.get()
                .uri("/tv/{id}",id)
                .retrieve()
                .body(TmdbTvDetails.class);
    }

    public TmdbSeasonDetails getSeasonDetails(Long tvId,
                                              Integer seasonNumber){
        return restClient.get().
                uri("/tv/{id}/season/{seasonNumber}",tvId,seasonNumber)
                .retrieve()
                .body(TmdbSeasonDetails.class);
    }
}
