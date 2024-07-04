package org.cheetah.crystal.redis.services;
import java.util.concurrent.TimeUnit;

import org.cheetah.crystal.dtos.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveUserToken(String token, User user) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(token, user, 1, TimeUnit.HOURS); // Il token scade dopo 1 ora
    }
    
    public void saveUserOtp(String otp, User user) {
    	ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
    	valueOps.set("OTP:"+user.getId(), otp, 60, TimeUnit.SECONDS); // Il token scade dopo 60 secondi
    	
    }
    
    public String getUserOtp(String userId) {
    	ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
    	return (String) valueOps.get("OTP:"+userId);
    }

    public User getUserByToken(String token) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return (User) valueOps.get(token);
    }

    public void deleteUserToken(String token) {
        redisTemplate.delete(token);
    }
}