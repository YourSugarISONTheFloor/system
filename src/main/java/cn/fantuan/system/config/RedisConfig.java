package cn.fantuan.system.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	//编写自己的redisTemplate
	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		//一般为了方便使用，一般用String，Object
		RedisTemplate<String, Object> template = new RedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);

		//json序列化配置
		FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

		//String的序列化
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		//key采用String的序列化反式
		template.setKeySerializer(stringRedisSerializer);
		//hash的key也采用String的序列化反式
		template.setHashKeySerializer(stringRedisSerializer);
		//value采用jackson序列化方式
		template.setValueSerializer(fastJsonRedisSerializer);
		//hash的value序列化反式采用jackson
		template.setHashValueSerializer(fastJsonRedisSerializer);

		template.afterPropertiesSet();
		return template;
	}
}
