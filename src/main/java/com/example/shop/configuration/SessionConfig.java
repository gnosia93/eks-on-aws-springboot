package com.example.shop.configuration;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

// @Configuration
// @EnableRedisHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {

    /*
    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(@Value("classpath:/redisson.yaml") Resource configFile) throws IOException {
        Config config = Config.fromYAML(configFile.getInputStream());
        return Redisson.create(config);
    }
    */
}