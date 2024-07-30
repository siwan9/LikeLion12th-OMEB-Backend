package batch.batch.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import batch.batch.redis.entity.EventReview;
import batch.batch.redis.entity.EventScore;
import batch.batch.redis.entity.EventView;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() { // Lettuce 와 Judis 가 있는데 요즘은 Lettuce 가 더 많이 사용되는거 같음
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory); // connectionFactory 를 설정해줘야 함
		template.setKeySerializer(new StringRedisSerializer()); // key 는 String 으로
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // value 는 Json 으로
		return template;
	}

	@Bean RedisTemplate<String, EventReview> redisTemplateReview(RedisConnectionFactory connectionFactory){
		// GenericJackson2JsonRedisSerializer 설정
		RedisTemplate<String, EventReview> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(EventReview.class));  // Value: 직렬화에 사용할 Object 사용하기
		return redisTemplate;
	}

	@Bean RedisTemplate<String, EventView> redisTemplateView(RedisConnectionFactory connectionFactory){
		// GenericJackson2JsonRedisSerializer 설정
		RedisTemplate<String, EventView> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(EventView.class));  // Value: 직렬화에 사용할 Object 사용하기
		return redisTemplate;
	}

	@Bean RedisTemplate<String, EventScore> redisTemplateScore(RedisConnectionFactory connectionFactory){
		// GenericJackson2JsonRedisSerializer 설정
		RedisTemplate<String, EventScore> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(EventScore.class));  // Value: 직렬화에 사용할 Object 사용하기
		return redisTemplate;
	}
}