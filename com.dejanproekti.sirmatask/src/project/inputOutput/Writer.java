package project.inputOutput;

public interface Writer {

    void write(String output);

    void writeLine(String output);

    void writeLine(String format, Object... params);

}
