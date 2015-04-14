package org.sagebionetworks.dashboard.config;

import java.util.Properties;

/**
 * Overwrites with command-line arguments. Stack-insensitive.
 */
public class SystemPropertiesProvider implements PropertiesProvider {

    public SystemPropertiesProvider(PropertiesProvider provider) {
        PropertyReader cmdArgsReader = new SystemPropertyReader();
        Properties original = provider.getProperties();
        properties = PropertiesUtils.mergeProperties(cmdArgsReader, original);
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    private final Properties properties;
}
