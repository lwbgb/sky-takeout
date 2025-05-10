package pers.lwb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SkyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyServerApplication.class, args);
    }

}
