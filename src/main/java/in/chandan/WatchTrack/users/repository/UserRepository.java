package in.chandan.WatchTrack.users.repository;

import in.chandan.WatchTrack.users.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
