package org.sagebionetworks.dashboard.dao;

import org.sagebionetworks.dashboard.parse.AccessRecord;

public interface AccessRecordDao {

    void put(AccessRecord record, String file_id);

    void cleanup();

    long count();
}
