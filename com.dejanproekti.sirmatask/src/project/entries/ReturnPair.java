package project.entries;

import project.models.Pair;

public final class ReturnPair {

    public ReturnPair() {
    }

    public static Pair execute(long firstEmployeeId, long secondEmployeeId, long overlapDuration) {
        return new Pair(
                firstEmployeeId,
                secondEmployeeId,
                overlapDuration);
    }
}
