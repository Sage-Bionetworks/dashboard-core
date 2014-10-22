package org.sagebionetworks.dashboard.dao;

public interface LogFileDao {

    void put(String filePath, int log_type);

    void cleanup();

    long count();
}
