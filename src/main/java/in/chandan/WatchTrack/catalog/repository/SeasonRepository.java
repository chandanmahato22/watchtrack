package in.chandan.WatchTrack.catalog.repository;

import in.chandan.WatchTrack.catalog.entity.Season;
import in.chandan.WatchTrack.catalog.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season,Long> {

    Optional<Season> findByTitleOrderBySeasonNumberAsc(Title title);

    Optional<Season> findByTitleAndSeasonNumber(Title title,
                                                Integer seasonNumber);
}
