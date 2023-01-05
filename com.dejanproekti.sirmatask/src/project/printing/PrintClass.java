package project.printing;

import project.printing.interfaces.Runnable;
import project.entries.RecordProcessing;
import project.inputOutput.FileInputOutput;
import project.inputOutput.Writer;
import project.models.OneRecord;
import project.models.Pair;
import project.service.EmployeeService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static project.constants.Constants.*;

public class PrintClass implements Runnable {

    private final FileInputOutput fileIO;
    private final Writer writer;
    private final EmployeeService emplService;

    public PrintClass(FileInputOutput fileIO, Writer writer, EmployeeService emplService) {
        this.fileIO = fileIO;
        this.writer = writer;
        this.emplService = emplService;
    }

    @Override
    public void run() throws IOException {
        //Read all records data from .txt file
        List<OneRecord> oneRecords = this.fileIO.read(FILE_PATH)
                .stream()
                .map(RecordProcessing::execute)
                .collect(Collectors.toList());

        //Save all employee records into "database"
        this.emplService.addEmployeeRecords(oneRecords);

        //Find all team, couple of employees which r worked under same project and have overlap
        List<Pair> pairs = this.emplService.findAllPairsWithOverlap();

        printResult(pairs);
    }

    private void printResult(List<Pair> pairs) throws IOException {
        if (pairs.size() != EMPTY_COLLECTION_SIZE) {
            pairs.sort((pair1, pair2) ->
                    (int) (pair2.getTotalDuration() - pair1.getTotalDuration()));
            Pair bestPair = pairs.get(INDEX_ZERO);

            this.writer.write(
                    String.format(LONGEST_PERIOD,
                            bestPair.getFirstEmployeeId(),
                            bestPair.getSecondEmployeeId(),
                            bestPair.getTotalDuration()));
        } else {
            this.writer.write(NO_EXIST_PAIR);
        }
    }
}
