package org.sagebionetworks.dashboard.config;

import java.util.Properties;

/**
 * Overwrites with command-line arguments. Stack-insensitive.
 */
public class SystemPropertiesProvider implements PropertiesProvider {

    public SystemPropertiesProvider(PropertiesProvider provider) {
        PropertyReader sysPropReader = new SystemPropertyReader();
        Properties original = provider.getProperties();
        properties = PropertiesUtils.mergeProperties(sysPropReader, original);
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    private final Properties properties;
}
