package quizportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"quizportal.data"})
@SpringBootApplication(scanBasePackages = {"quizportal"}, exclude = {SecurityAutoConfiguration.class})
public class QuizPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizPortalApplication.class, args);
    }
}
