package ro.sci.requestweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RequestWebApp {
    public static void main(String[] args) {
        SpringApplication.run(RequestWebApp.class, args);
    }

}
