package org.sagebionetworks.dashboard.config;

import java.util.Properties;

/**
 * Overwrites with command-line arguments. Stack-insensitive.
 */
public class EnvPropertiesProvider implements PropertiesProvider {

    public EnvPropertiesProvider(PropertiesProvider provider) {
        PropertyReader envReader = new EnvPropertyReader();
        Properties original = provider.getProperties();
        properties = PropertiesUtils.mergeProperties(envReader, original);
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    private final Properties properties;
}
