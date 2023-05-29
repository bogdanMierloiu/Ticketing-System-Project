package ro.sci.requestweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class RequestWebApp {

    @Bean(name = "personCacheManager")
    public CacheManager personCacheManger() {
        return new ConcurrentMapCacheManager("structures", "subunits", "departments", "ranks", "request-types", "specialists");
    }

    public static void main(String[] args) {
        SpringApplication.run(RequestWebApp.class, args);
    }

}
