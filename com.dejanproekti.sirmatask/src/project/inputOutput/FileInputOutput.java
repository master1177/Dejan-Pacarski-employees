package project.inputOutput;

import java.util.List;

public interface FileInputOutput {

    List<String> read(String file);

    void write(String fileContent, String file);

}
