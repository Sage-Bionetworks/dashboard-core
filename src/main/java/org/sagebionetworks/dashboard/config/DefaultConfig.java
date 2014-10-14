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

    private final Stack stack;
    private final Properties properties;

    public DefaultConfig(final String configFile) throws IOException {
        // Read about the stack
        PropertiesProvider stackProvider =
                new CommandArgsPropertiesProvider(
                new EnvPropertiesProvider(
                new FilePropertiesProvider(new File(configFile),
                new BasicPropertiesProvider())));
        Properties propertiesForStack = stackProvider.getProperties();
        String stackName = propertiesForStack.getProperty(PropertyReader.STACK);
        if (stackName != null) {
            stack = Stack.valueOf(stackName.toUpperCase());
        } else {
            stack = Stack.LOCAL;
        }
        String stackPassword = propertiesForStack.getProperty(PropertyReader.STACK_PASSWORD);
        // Set up the properties
        PropertiesProvider provider = createProvider(stack, stackPassword, configFile);
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

    private PropertiesProvider createProvider(final Stack stack, final String stackPassword,
            final String configFile) throws IOException {
        if (stackPassword != null && !stackPassword.isEmpty()) {
            return
                    new CommandArgsPropertiesProvider(
                    new EnvPropertiesProvider(
                    new DecryptedPropertiesProvider(stackPassword,
                    new StackPropertiesProvider(stack,
                    new FilePropertiesProvider(new File(configFile))))));
        }
        return
                new CommandArgsPropertiesProvider(
                new EnvPropertiesProvider(
                new StackPropertiesProvider(stack,
                new FilePropertiesProvider(new File(configFile)))));
    }
}
