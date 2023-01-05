package project.service;

import project.models.OneRecord;
import project.models.Pair;

import java.util.List;

public interface EmployeeService {

    void addEmployeeRecords(List<OneRecord> oneRecords);

    List<Pair> findAllPairsWithOverlap();
}
