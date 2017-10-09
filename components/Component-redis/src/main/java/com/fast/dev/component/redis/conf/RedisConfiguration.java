package com.fast.dev.component.redis.conf;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fast.dev.component.redis.model.RedisConfig;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

	/**
	 * 配置 JedisPoolConfig
	 * 
	 * @param redisConfig
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig(RedisConfig redisConfig) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		BeanUtils.copyProperties(redisConfig.getPool(), jedisPoolConfig);
		return jedisPoolConfig;
	}

	/**
	 * 配置 JedisConnectionFactory
	 * 
	 * @param redisConfig
	 * @return
	 */
	@Bean(destroyMethod = "destroy")
	public JedisConnectionFactory jedisConnectionFactory(RedisConfig redisConfig) {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig(redisConfig));
		BeanUtils.copyProperties(redisConfig.getDataSource(), jedisConnectionFactory);
		return jedisConnectionFactory;
	}

	/**
	 * 配置 RedisTemplate
	 * 
	 * @param redisConfig
	 * @return
	 */
	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConfig redisConfig) {
		RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
		// ConnectionFactory
		redisTemplate.setConnectionFactory(jedisConnectionFactory(redisConfig));
//		// KeySerializer
		redisTemplate.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
		// HashKeySerializer
		redisTemplate.setHashKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
		// ValueSerializer
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		// HashValueSerializer
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		//允许事务
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}

}
