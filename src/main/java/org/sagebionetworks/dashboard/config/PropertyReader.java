package org.sagebionetworks.dashboard.config;

public interface PropertyReader {

    String SEPARATOR = ".";

    // Predefined property names
    String STACK = "stack";

    /**
     * Reads the property value given a key. Null if the key does not exist.
     */
    String read(String key);
}
