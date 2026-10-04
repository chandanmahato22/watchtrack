package in.chandan.WatchTrack.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    String usernameOrEmail;
    String password;
}
