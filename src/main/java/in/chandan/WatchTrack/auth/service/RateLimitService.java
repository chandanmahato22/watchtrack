package in.chandan.WatchTrack.auth.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {

    private StringRedisTemplate stringRedisTemplate;
    final private int MAX_LIMIT = 5;
    final private Duration duration = Duration.ofMinutes(1);


    public RateLimitService(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public boolean isBlocked(String ip){
        String key = "failed:attempt:ip:" + ip;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null ) return false;

        return Integer.parseInt(value) > MAX_LIMIT;
    }

    public void increment(String ip){
        String key = "failed:attempt:ip:" + ip;
        long value = stringRedisTemplate.opsForValue().increment(key);
            if(value == 1){
                stringRedisTemplate.expire(key,duration);
            }
    }

    public void resetLimit(String ip){
        String key = "failed:attempt:ip:" + ip;
        stringRedisTemplate.delete(key);
    }

    public void recordFailedAttempt(String ip){
        String key = "failed:attempt:ip:" + ip;
        increment(ip);
    }
}
