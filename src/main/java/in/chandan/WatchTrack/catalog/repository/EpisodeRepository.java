package in.chandan.WatchTrack.catalog.repository;

import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.catalog.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {

    Optional<Episode> findBySeasonAndEpisodeNumber(Season Season,
                                                   Integer episodeNumber);

    List<Episode> findBySeasonOrderByEpisodeNumberAsc(Season season);
}
