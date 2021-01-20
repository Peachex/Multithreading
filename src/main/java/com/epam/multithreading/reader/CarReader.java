package com.epam.multithreading.reader;

import com.epam.multithreading.exception.ReaderException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarReader {
    private final static Logger logger = LogManager.getLogger();
    private static final String DEFAULT_PATH = "data\\cars.txt";

    public List<String> readDataFromFile(String path) throws ReaderException {
        if (path == null) {
            path = DEFAULT_PATH;
            logger.log(Level.ERROR, "Path is null, default path will be used.");
        }
        Path filePath = Paths.get(path);
        try {
            if (!(Files.exists(filePath) && Files.size(filePath) > 0)) {
                filePath = Paths.get(DEFAULT_PATH);
                logger.log(Level.ERROR, "Wrong user file, default file will be used.");
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
        List<String> lines;
        try (Stream<String> lineStream = Files.lines(filePath)) {
            lines = lineStream.collect(Collectors.toList());
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
            throw new ReaderException(e.getMessage());
        }
        return lines;
    }
}
