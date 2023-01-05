package project;

import project.printing.PrintClass;
import project.inputOutput.ConsoleOutputWriter;
import project.inputOutput.FileInputOutput;
import project.inputOutput.FileInputOutputmpl;
import project.inputOutput.Writer;
import project.repository.EmployeeRepository;
import project.repository.EmployeeRepositoryImpl;
import project.service.EmployeeService;
import project.service.EmployeeServiceImpl;

import java.io.*;

public class MainClass {
    public static void main(String[] args) throws IOException {

        FileInputOutput fileIO = new FileInputOutputmpl();
        Writer writer = new ConsoleOutputWriter();
        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
        EmployeeService emplService = new EmployeeServiceImpl(employeeRepository);

        PrintClass engine = new PrintClass(fileIO, writer, emplService);
        engine.run();
    }
}
