package in.chandan.WatchTrack.excepitons;

public class TooManyRequestException extends RuntimeException{
    public TooManyRequestException(String msg){
        super(msg);
    }
}
