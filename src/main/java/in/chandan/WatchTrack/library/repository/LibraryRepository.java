package in.chandan.WatchTrack.library.repository;


import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.enums.LibraryStatus;
import in.chandan.WatchTrack.library.entity.UserLibrary;
import in.chandan.WatchTrack.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<UserLibrary,Long> {
    List<UserLibrary> findByUser(User user);
    Optional<UserLibrary> findByUserAndTitle(User user, Title title);
    Optional<UserLibrary> findByIdAndUser(Long id, User user);
    List<UserLibrary> findByStatusIn(List<LibraryStatus> statuses);

    List<UserLibrary> findByTitleAndStatusIn(
            Title title,
            List<LibraryStatus> statuses
    );
}
