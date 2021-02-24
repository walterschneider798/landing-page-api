package ch.schneider.landingpageapi.config;

import ch.schneider.landingpageapi.model.WebsiteOwner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
  /**
   * @param connectionFactory
   * @return the connection
   */
  @Bean
  public RedisTemplate<String, WebsiteOwner> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, WebsiteOwner> template = new RedisTemplate<>();
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    template.setConnectionFactory(connectionFactory);
    return template;
  }
}