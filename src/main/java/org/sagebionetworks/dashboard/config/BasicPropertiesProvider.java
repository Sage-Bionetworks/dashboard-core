package org.sagebionetworks.dashboard.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets the stack to LOCAL if it does not already exist.
 */
public class BasicPropertiesProvider implements PropertiesProvider {

    private final Logger logger = LoggerFactory.getLogger(BasicPropertiesProvider.class);

    private final Properties properties;

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
        logger.info("Stack set to " + properties.getProperty(PropertyReader.STACK));
        return properties;
    }
}
