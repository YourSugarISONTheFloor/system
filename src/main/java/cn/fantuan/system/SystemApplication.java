package cn.fantuan.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SystemApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        LOGGER.info(SystemApplication.class.getSimpleName() + " SUCCESS FOR IN");
    }
}