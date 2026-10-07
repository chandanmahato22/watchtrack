package in.chandan.WatchTrack.auth.dto;

import in.chandan.WatchTrack.users.enums.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegDto {

    private String username;

    private String email;

    private String password;

    private String role;
}
