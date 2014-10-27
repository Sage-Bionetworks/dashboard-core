package org.sagebionetworks.dashboard.dao;

public interface LogFileDao {

    void put(String filePath, String id, int logType);
    void cleanup();
    long count();
    void update(String id);
    boolean isCompleted(String filePath);
}
