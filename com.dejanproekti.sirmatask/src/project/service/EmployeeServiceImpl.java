package project.service;

import project.models.OneRecord;
import project.models.Pair;
import project.entries.ReturnPair;
import project.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static project.constants.Constants.*;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository emplRepo;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.emplRepo = employeeRepository;
    }

    @Override
    public void addEmployeeRecords(List<OneRecord> oneRecords) {
        this.emplRepo.saveAll(oneRecords);
    }

    @Override
    public List<Pair> findAllPairsWithOverlap() {
        List<OneRecord> allOneRecords = this.emplRepo.getAllRecords();

        List<Pair> pairs = new ArrayList<>();
        for (int i = INDEX_ZERO; i < allOneRecords.size() - ONE; i++) {
            for (int j = i + ONE; j < allOneRecords.size(); j++) {
                OneRecord firstEmpl = allOneRecords.get(i);
                OneRecord secondEmpl = allOneRecords.get(j);

                if (firstEmpl.getProjectId() == secondEmpl.getProjectId()
                        && hasOverlap(firstEmpl, secondEmpl)) {
                    long overlapDays = calculateOverlap(firstEmpl, secondEmpl);

                    if (overlapDays > DEFAULT_OVERLAP_ZERO_DAYS) {
                        updateTeamCollection(pairs, firstEmpl, secondEmpl, overlapDays);
                    }
                }
            }
        }
        return pairs;
    }

    private long calculateOverlap(OneRecord firstEmpl, OneRecord secondEmpl) {
        LocalDate periodStartDate =
                firstEmpl.getDateFrom().isBefore(secondEmpl.getDateFrom()) ?
                        secondEmpl.getDateFrom() : firstEmpl.getDateFrom();

        LocalDate periodEndDate =
                firstEmpl.getDateTo().isBefore(secondEmpl.getDateTo()) ?
                        firstEmpl.getDateTo() : secondEmpl.getDateTo();

        return Math.abs(ChronoUnit.DAYS.between(periodStartDate, periodEndDate));
    }

    private boolean hasOverlap(OneRecord firstEmpl, OneRecord secondEmpl) {
        return (firstEmpl.getDateFrom().isBefore(secondEmpl.getDateTo())
                || firstEmpl.getDateFrom().isEqual(secondEmpl.getDateTo()))
                && (firstEmpl.getDateTo().isAfter(secondEmpl.getDateFrom())
                || firstEmpl.getDateTo().isEqual(secondEmpl.getDateFrom()));
    }

    private boolean isTeamPresent(Pair pair, long firstEmplId, long secondEmplId) {
        return ( pair.getFirstEmployeeId() == firstEmplId
                && pair.getSecondEmployeeId() == secondEmplId )
                || ( pair.getFirstEmployeeId() == secondEmplId
                && pair.getSecondEmployeeId() == firstEmplId );
    }

    private void updateTeamCollection(List<Pair> pairs, OneRecord firstEmpl, OneRecord secondEmpl, long overlapDays) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        //If the team is present -> update team's total overlap
        pairs.forEach(pair -> {
            if (isTeamPresent(pair, firstEmpl.getEmployeeId(), secondEmpl.getEmployeeId())) {
                pair.addOverlapDuration(overlapDays);
                isPresent.set(true);
            }
        });

        if (!isPresent.get()) {
            Pair newPair = ReturnPair.execute(
                    firstEmpl.getEmployeeId(),
                    secondEmpl.getEmployeeId(),
                    overlapDays);
            pairs.add(newPair);
        }
    }
}
