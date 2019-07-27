package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class TxtFileWriter {
    private String fileName;
    private static final Logger logger = Logger.getLogger(Logger.class.getName());

    public TxtFileWriter(String fileName) {
        this.fileName = fileName;
    }

    public void write(String line) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))) {
            bufferedWriter.write(line);
        } catch (IOException e) {
            logger.warning("Error!" + e.getMessage());
        }
    }
}

