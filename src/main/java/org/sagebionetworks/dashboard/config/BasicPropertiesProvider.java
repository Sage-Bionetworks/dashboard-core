package org.sagebionetworks.dashboard.config;

import java.util.Properties;

public class BasicPropertiesProvider implements PropertiesProvider {

    public BasicPropertiesProvider() {
        properties = getBasicProperties();
    }

    public BasicPropertiesProvider(Properties properties) {
        Properties basic = getBasicProperties();
        this.properties = PropertiesUtils.mergeProperties(properties, basic);
    }

    @Override
    public Properties getProperties() {
        return new Properties(properties);
    }

    private Properties getBasicProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyReader.STACK, Stack.LOCAL.name());
        return properties;
    }

    private final Properties properties;
}
