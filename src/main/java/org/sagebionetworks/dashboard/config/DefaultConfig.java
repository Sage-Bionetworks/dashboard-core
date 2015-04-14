package org.sagebionetworks.dashboard.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>Load properties in the following order:
 * <ol>
 *   <li>Load all the properties defined in the specified configuration file.
 *   <li>If a property is for the specified stack, it is chosen over the default value; otherwise,
 *       the default value is used.
 *   <li>Overwrite properties with environment variables.
 *   <li>Overwrite properties with command-line arguments.
 * </ol>
 */
public class DefaultConfig extends AbstractConfig {

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
        } else {
            stack = Stack.LOCAL;
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
            aggregatedProperties = new FilePropertiesProvider(new File(configFile), aggregatedProperties);
        }
        return aggregatedProperties;
    }

    private final Stack stack;
    private final Properties properties;
}
