package in.chandan.WatchTrack.auth.controller;

import in.chandan.WatchTrack.auth.dto.LoginDto;
import in.chandan.WatchTrack.auth.service.AuthService;
import in.chandan.WatchTrack.users.dto.RegDto;
import in.chandan.WatchTrack.users.dto.RegRespDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final private AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody
                                        RegDto req){
            String resp = authService.register(req);

           return  ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto req){
        String resp = authService.verifyUser(req);

        return ResponseEntity.ok(resp);
    }
}
