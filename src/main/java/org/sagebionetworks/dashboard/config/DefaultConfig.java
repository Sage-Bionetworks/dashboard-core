package org.sagebionetworks.dashboard.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Load properties in the following order:
 * <ol>
 *   <li>Load all the properties defined in the specified configuration files.
 *   <li>If a property is for the specified stack, it is chosen over the default value; otherwise,
 *       the default value is used.
 *   <li>Overwrite properties with environment variables.
 *   <li>Overwrite properties with command-line arguments.
 * </ol>
 */
public class DefaultConfig extends AbstractConfig {

    private final Logger logger = LoggerFactory.getLogger(DefaultConfig.class);

    private final Stack stack;
    private final Properties properties;

    /**
     * List of config files, "file0, file1, file2, ...", file1 will overwrite file0,
     * file2 will overwrite file1, so on and so forth.
     */
    public DefaultConfig(final String... configFiles) throws IOException {
        // Aggregate properties from files
        final PropertiesProvider filePropertiesProvider = aggregateFiles(configFiles);
        // Further aggregate with environment variables and system properties
        final PropertiesProvider propertiesProvider = new SystemPropertiesProvider(
                new EnvPropertiesProvider(filePropertiesProvider));
        // Read the stack
        final String stackName = propertiesProvider.getProperties().getProperty(PropertyReader.STACK);
        if (stackName != null) {
            stack = Stack.valueOf(stackName.toUpperCase());
            logger.info("Stack is " + stack);
        } else {
            stack = Stack.LOCAL;
            logger.info("Stack is missing. Default to" + Stack.LOCAL.name() + ".");
        }
        // Set up the final properties based on the stack
        final PropertiesProvider provider = new SystemPropertiesProvider(
                new EnvPropertiesProvider(
                new StackPropertiesProvider(stack, filePropertiesProvider)));
        properties = provider.getProperties();
    }

    @Override
    public Stack getStack() {
        return stack;
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    private PropertiesProvider aggregateFiles(final String... configFiles) throws IOException {
        PropertiesProvider aggregatedProperties = new BasicPropertiesProvider();
        for (String configFile : configFiles) {
            final File file = new File(configFile);
            if (file.exists()) {
                logger.info("Adding config file " + file.getPath());
                aggregatedProperties = new FilePropertiesProvider(file, aggregatedProperties);
            } else {
                logger.warn("Missing config file " + file.getPath() + ". File is skipped.");
            }
        }
        return aggregatedProperties;
    }
}
