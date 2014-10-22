package org.sagebionetworks.dashboard.dao;

public interface LogFileDao {

    void put(String filePath);

    void cleanup();

    long count();
}
