package org.sagebionetworks.dashboard.dao;

public interface LogFileDao {

    void put(String filePath, int logType);

    void cleanup();

    long count();
}
