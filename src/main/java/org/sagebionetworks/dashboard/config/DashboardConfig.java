package org.sagebionetworks.dashboard.config;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("dashboardConfig")
public class DashboardConfig implements Config {

    public DashboardConfig() {
        try {
            String userHome = System.getProperty("user.home");
            File configFile = new File(userHome + "/.dashboard/dashboard.config");
            if (!configFile.exists()) {
                logger.warn("Missing config file " + configFile.getPath());
                // This file is needed as the source of properties
                // which should be overwritten by environment variables
                // or command-line arguments
                configFile = new File(getClass().getResource("/META-INF/dashboard.config").getFile());
            }
            config = new DefaultConfig(configFile.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String key) {
        return config.get(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    @Override
    public int getInt(String key) {
        return config.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return config.getLong(key);
    }

    @Override
    public Stack getStack() {
        return config.getStack();
    }

    public String getAccessRecordBucket() {
        return config.get("access.record.bucket");
    }

    public String getAwsAccessKey() {
        return config.get("aws.access.key");
    }

    public String getAwsSecretKey() {
        return config.get("aws.secret.key");
    }

    public String getDwUsername() {
        return config.get("dw.username");
    }

    public String getDwPassword() {
        return config.get("dw.password");
    }

    public String getSynapseUser() {
        return config.get("synapse.user");
    }

    public String getSynapsePassword() {
        return config.get("synapse.password");
    }

    private final Logger logger = LoggerFactory.getLogger(DashboardConfig.class);
    private final Config config;
}
