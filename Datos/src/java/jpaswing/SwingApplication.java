package jpaswing;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * This is the Spring Boot Application class.  This is where we make sure we're NOT running in Headless mode and that
 * the WebApplicationType is set to NONE.
 */
@SpringBootApplication
public class SwingApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SwingApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}

