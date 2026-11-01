package in.chandan.WatchTrack.excepitons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private int status;
    private String msg;
    private String path;
    private String error;
}
