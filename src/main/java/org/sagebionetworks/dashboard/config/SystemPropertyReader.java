package org.sagebionetworks.dashboard.config;

public class SystemPropertyReader implements PropertyReader {

    @Override
    public String read(final String key) {
        return System.getProperty(key);
    }
}
