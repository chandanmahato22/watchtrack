package in.chandan.WatchTrack.auth.service;

import in.chandan.WatchTrack.auth.entity.PrincipalUser;
import in.chandan.WatchTrack.users.entity.User;
import in.chandan.WatchTrack.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    final private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username,username).orElseThrow(
                () -> new RuntimeException("user Not found")
        );
        return new PrincipalUser(user);
    }
}
