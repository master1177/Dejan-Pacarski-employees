package project.entries;

import project.models.OneRecord;

import java.time.LocalDate;

public final class RecordProcessing {

    private static final String DEFAULT_REGEX_PATTERN = ", ";
    private static final String NULL_STR = "NULL";
    private static final int INDEX_ZERO = 0;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;

    public RecordProcessing() {
    }

    public static OneRecord execute(String line) {
        String[] recordArgs = line.split(DEFAULT_REGEX_PATTERN);

        long emplID = Long.parseLong(recordArgs[INDEX_ZERO].trim());
        long projectID = Long.parseLong(recordArgs[INDEX_ONE].trim());

        LocalDate dateFrom = LocalDate.parse(recordArgs[INDEX_TWO]);

        LocalDate dateTo;
        if (recordArgs[INDEX_THREE] == null || NULL_STR.equals(recordArgs[INDEX_THREE])) {
            dateTo = LocalDate.now();
        } else {
            dateTo = LocalDate.parse(recordArgs[INDEX_THREE]);
        }

        return new OneRecord(
                emplID,
                projectID,
                dateFrom,
                dateTo
        );
    }
}
