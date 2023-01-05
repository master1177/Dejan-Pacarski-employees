package project.repository;

import project.models.OneRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private List<OneRecord> database;

    public EmployeeRepositoryImpl() {
        this.database = new ArrayList<>();
    }

    @Override
    public void save(OneRecord oneRecord) {
        this.database.add(oneRecord);
    }

    @Override
    public void saveAll(Collection<OneRecord> oneRecords) {
        this.database.addAll(oneRecords);
    }

    @Override
    public List<OneRecord> getAllRecords() {
        return Collections.unmodifiableList(this.database);
    }
}
