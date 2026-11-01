package in.chandan.WatchTrack.auth.service;

import in.chandan.WatchTrack.auth.dto.LoginDto;
import in.chandan.WatchTrack.auth.dto.RegDto;
import in.chandan.WatchTrack.excepitons.ResourceNotFoundException;
import in.chandan.WatchTrack.excepitons.TooManyRequestException;
import in.chandan.WatchTrack.excepitons.UnauthorizedException;
import in.chandan.WatchTrack.users.entity.User;
import in.chandan.WatchTrack.users.enums.Roles;
import in.chandan.WatchTrack.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private RateLimitService rateLimitService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RateLimitService rateLimitService){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.rateLimitService = rateLimitService;
    }

    public String register(RegDto req) {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setRole(Roles.valueOf("USER"));
        String rawPass = req.getPassword();
        String hassPass = passwordEncoder.encode(rawPass);

        user.setPassword(hassPass);
        userRepository.save(user);

        return "Register successful for " + req.getUsername();
    }

    public String verifyUser(LoginDto req, HttpServletRequest servletRequest)  {
        String ip = servletRequest.getRemoteAddr();
        if(rateLimitService.isBlocked(ip)){
            throw new TooManyRequestException("Too Many Failed Attempt try again later");
        }

        try {
        User user = userRepository.findByUsernameOrEmail(req.getUsernameOrEmail()
                ,req.getUsernameOrEmail()).orElseThrow(
                    () -> new ResourceNotFoundException("user with " + req.getUsernameOrEmail() + " not found")
            );
            if(passwordEncoder.matches(req.getPassword(),user.getPassword())) {
                rateLimitService.resetLimit(ip);
                return jwtService.getToken(req);
            }
            else {
                throw new UnauthorizedException("wrong credentials");
            }
        }
        catch (RuntimeException ex){
            rateLimitService.recordFailedAttempt(ip);
            throw ex;
        }
    }

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("user with username :" + username + "not found")
        );
        return user;
    }

}
