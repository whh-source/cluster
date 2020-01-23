package com.whh.rediscluster;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class RedisClusterApplicationTests {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void setTest(){

	}

	@Test
	public void hashTest(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", "wanghuaihong");
		map.put("sex", 1);
		redisTemplate.opsForHash().putAll("worker", map);
		redisTemplate.opsForHash().put("worker", "old", 18);

		String name = (String) redisTemplate.opsForHash().get("worker", "name");
		Integer sex = (Integer) redisTemplate.opsForHash().get("worker", "sex");

		Integer old = (Integer) redisTemplate.opsForHash().get("worker", "old");

		log.info("worker: {}, sex:{}, old: {}", name, sex, old);

	}

	@Test
	public void listTest(){
		redisTemplate.opsForList().rightPush("list1", "212");
		String key = (String) redisTemplate.opsForList().leftPop("list1");
		log.info("key:{}", key);

		//等待3秒返回
		String value = (String)redisTemplate.opsForList().leftPop("list1", 3, TimeUnit.SECONDS);
		log.info("key:{}", value);


		//立刻返回
		value = (String)redisTemplate.opsForList().leftPop("list1");
		log.info("key:{}", value);


	}

	@Test
	public void stringTest(){
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		if (valueOperations != null) {
			Boolean success = valueOperations.setIfAbsent("asdf", "3425", 8, TimeUnit.HOURS);
			if (success != null && success){
				log.info("success");
			}else {
				log.info("fail");
			}

			redisTemplate.opsForValue().set("asdf3", 13);
			redisTemplate.opsForValue().increment("asdf3");

			Integer value = (Integer) valueOperations.get("asdf3");
			log.info("value: {}", value);

			redisTemplate.opsForValue().set("jj3", "asdfasdf");

			String tmp = (String) redisTemplate.opsForValue().get("jj3");
			log.info("value: {}", tmp);

			String oldValue = (String) redisTemplate.opsForValue().getAndSet("jj3", "sd");
			log.info("value: {}", oldValue);
		}
	}


}
