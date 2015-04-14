package org.sagebionetworks.dashboard.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilePropertiesProvider implements PropertiesProvider {

    private final Logger logger = LoggerFactory.getLogger(FilePropertiesProvider.class);

    public FilePropertiesProvider(File file) throws IOException {
        this(file, new BasicPropertiesProvider());
    }

    public FilePropertiesProvider(File file, PropertiesProvider original) throws IOException {
        logger.info("Reading config from file " + file.getPath());
        try (InputStream inputStream = new FileInputStream(file)) {
            InputStreamPropertiesProvider provider = new InputStreamPropertiesProvider(inputStream);
            properties = PropertiesUtils.mergeProperties(
                    provider.getProperties(), original.getProperties());
        }
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    private final Properties properties;
}
