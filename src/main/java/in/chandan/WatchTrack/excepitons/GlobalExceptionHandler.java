package in.chandan.WatchTrack.excepitons;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,
                                                 HttpServletRequest req){
        ErrorResponse err = new ErrorResponse();

        err.setMsg(ex.getMessage());
        err.setPath(req.getRequestURI());
        err.setStatus(404);
        err.setError("NOT FOUND");
        err.setTimeStamp(LocalDateTime.now());

        return ResponseEntity.status(404).body(err);
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex,
                                                 HttpServletRequest req){
        ErrorResponse err = new ErrorResponse();

        err.setMsg(ex.getMessage());
        err.setPath(req.getRequestURI());
        err.setStatus(400);
        err.setError("CONFLICT");
        err.setTimeStamp(LocalDateTime.now());

        return ResponseEntity.status(400).body(err);
    }

    @ExceptionHandler(TooManyRequestException.class)
    ResponseEntity<ErrorResponse> handleBadRequest(TooManyRequestException ex,
                                                   HttpServletRequest req){
        ErrorResponse err = new ErrorResponse();

        err.setMsg(ex.getMessage());
        err.setPath(req.getRequestURI());
        err.setStatus(429);
        err.setError("TOO MANY REQUEST");
        err.setTimeStamp(LocalDateTime.now());

        return ResponseEntity.status(429).body(err);
    }

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<ErrorResponse> handleBadRequest(UnauthorizedException ex,
                                                   HttpServletRequest req){
        ErrorResponse err = new ErrorResponse();

        err.setMsg(ex.getMessage());
        err.setPath(req.getRequestURI());
        err.setStatus(401);
        err.setError("UNAUTHORIZED");
        err.setTimeStamp(LocalDateTime.now());

        return ResponseEntity.status(401).body(err);
    }
}
