package quizportal.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class ZoneConfig {

    private static final Logger LOGGER = LogManager.getLogger(ZoneConfig.class);

    @PostConstruct
    void started() {
        LOGGER.debug("setting JVM Default Timezone to UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
