package com.kotlin.board.config.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val redisBlackListTemplate: RedisTemplate<String, Any>,
) {

    fun set(key: String, o: Any, minutes: Int) {
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(o.javaClass)
        redisTemplate.opsForValue().set(key, o, minutes.toLong(), TimeUnit.MINUTES)
    }

    fun get(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun delete(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key)
    }

    fun setBlackList(key: String, o: Any, minutes: Int) {
        redisBlackListTemplate.valueSerializer = Jackson2JsonRedisSerializer(o.javaClass)
        redisBlackListTemplate.opsForValue().set(key, o, minutes.toLong(), TimeUnit.MINUTES)
    }

    fun getBlackList(key: String): Any? {
        return redisBlackListTemplate.opsForValue().get(key)
    }

    fun deleteBlackList(key: String): Boolean {
        return redisBlackListTemplate.delete(key)
    }
    fun hasKeyBlackList(key: String): Boolean {
        return redisBlackListTemplate.hasKey(key)
    }

 }