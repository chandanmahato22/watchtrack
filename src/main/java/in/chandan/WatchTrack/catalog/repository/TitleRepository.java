package in.chandan.WatchTrack.catalog.repository;

import in.chandan.WatchTrack.enums.Provider;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.enums.TitleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends JpaRepository<Title,Long> {

    Optional<Title> findByExternalIdAndProvider(
            Long externalId,
            Provider provider
    );

    List<Title> findByType(TitleType type);
}
