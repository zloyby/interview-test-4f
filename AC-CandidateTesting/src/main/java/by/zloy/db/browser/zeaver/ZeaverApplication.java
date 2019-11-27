package by.zloy.db.browser.zeaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ZeaverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeaverApplication.class, args);
    }
}
