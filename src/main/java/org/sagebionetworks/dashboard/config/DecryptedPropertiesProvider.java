package org.sagebionetworks.dashboard.config;

import java.util.Properties;

public class DecryptedPropertiesProvider implements PropertiesProvider {

    public DecryptedPropertiesProvider(String stackPassword, PropertiesProvider provider) {
        encryptor = new AesEncryptor(stackPassword);
        final Properties original = provider.getProperties();
        final Properties decrypted = new Properties();
        for (final String key : original.stringPropertyNames()) {
            String value = decrypt(original.getProperty(key));
            decrypted.setProperty(key, value);
        }
        properties = decrypted;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    private String decrypt(final String value) {
        if (value.startsWith(PREFIX)) {
            String stripped = value.substring(PREFIX.length());
            String decrypted = encryptor.decrypt(stripped);
            return decrypted;
        }
        return value;
    }

    static final String PREFIX = "SECURE+";
    private final AesEncryptor encryptor;
    private final Properties properties;
}
