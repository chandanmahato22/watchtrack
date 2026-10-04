package in.chandan.WatchTrack.auth.service;

import in.chandan.WatchTrack.auth.dto.LoginDto;
import in.chandan.WatchTrack.users.dto.RegDto;
import in.chandan.WatchTrack.users.entity.User;
import in.chandan.WatchTrack.users.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String register(RegDto req) {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());

        String rawPass = req.getPassword();
        String hassPass = passwordEncoder.encode(rawPass);

        user.setPassword(hassPass);
        userRepository.save(user);

        return "Register successful for " + req.getUsername();
    }

    public String verifyUser(LoginDto req) {

        User user = userRepository.findByUsernameOrEmail(req.getUsernameOrEmail()
                ,req.getUsernameOrEmail()).orElseThrow(
                    () -> new RuntimeException("user not found")
            );

        if(passwordEncoder.matches(req.getPassword(),user.getPassword())){
            return "you are logged in as "+ user.getUsername();
        }
        else {
            return "wrong credential ";
        }

    }
}
