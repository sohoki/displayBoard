package com.display.backoffice.util.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

	
	private final RedisTemplate<String, String> redisTemplate;
	
	
	public Boolean isKeyNullCheck(String key) {
		if (redisTemplate.hasKey(key)) {
			if (redisTemplate.getExpire(key) < 0) {
				redisTemplate.delete(key);
				return false;
			}else {
				return true;
			}
		}else {
			return false;
		}
		
	}
	public Boolean keyDelete(String key) {
		try {
			if (redisTemplate.hasKey(key))
			redisTemplate.delete(key);
			return true;
		}catch (Exception e) {
			log.error("keyDelete error:" + e.toString());
			return false;
		}
	}
}
