package quizportal.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceNotFoundException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger(ResourceNotFoundException.class);
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "%s not found: %s";
    private static final String RESOURCE_NOT_FOUND_BY_FIELD_MESSAGE = "%s not found: '%s' = '%s'";

    private static String buildMessage(String resourceName, String fieldName, String value) {
        resourceName = resourceName.replace("_", " ");
        String message = String.format(RESOURCE_NOT_FOUND_BY_FIELD_MESSAGE, resourceName, fieldName, value);
        LOGGER.warn(message);
        return message;
    }

    private static String buildMessage(String resourceName, String message) {
        resourceName = resourceName.replace("_", " ");
        message = String.format(RESOURCE_NOT_FOUND_MESSAGE, resourceName, message);
        LOGGER.warn(message);
        return message;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String value) {
        super(buildMessage(resourceName, fieldName, value));
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        this(resourceName, "id", String.valueOf(id));
    }
    public ResourceNotFoundException(Long id) {
        this( "id", String.valueOf(id));
    }

    public ResourceNotFoundException(String resourceName, String message) {
        super(buildMessage(resourceName, message));
    }

}
