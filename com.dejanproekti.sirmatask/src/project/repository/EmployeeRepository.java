package project.repository;

import project.models.OneRecord;

import java.util.Collection;
import java.util.List;

public interface EmployeeRepository {

    void save(OneRecord oneRecord);

    void saveAll(Collection<OneRecord> oneRecords);

    List<OneRecord> getAllRecords();
}
