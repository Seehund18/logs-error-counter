import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ErrorCounter {
    private Path logsDir;
    private Path outFile;

    private String errorPattern;
    private TimeToIntervalMapper resolver;
    private LogsDateTime logsDateTime;


    public ErrorCounter(Path logsDir, Path outFile, TimeToIntervalMapper resolver, String errorPattern, LogsDateTime logsDateTime) {
        if (!logsDir.toFile().isDirectory()) {
            throw new IllegalArgumentException("Directory should be given as input");
        }
        if (!outFile.toFile().isFile()) {
            throw new IllegalArgumentException("File should be given as output");
        }

        this.logsDir = logsDir;
        this.resolver = resolver;
        this.logsDateTime = logsDateTime;
        this.errorPattern = errorPattern;
        this.outFile = outFile;
    }

    public void countErrors() {
        Map<String, Long> errorsInIntervalMap = getFilesFromFolder(logsDir)
                .flatMap(this::getLinesInFile)
                .filter(str -> str.matches(errorPattern))
                .map(logsDateTime::parseDateTime)
                .collect(Collectors.groupingBy(resolver::mapTimeToInterval, TreeMap::new, Collectors.counting()));

        try {
            Files.deleteIfExists(outFile);
        } catch (IOException e) {
            throw new ErrorCounterException("Error while deleting file: " + outFile.toString(), e);
        }

        errorsInIntervalMap.forEach(this::writeToStatistic);
    }

    private Stream<Path> getFilesFromFolder(Path dir) {
        try {
            return Files.walk(dir)
                    .filter(file -> file.toFile().isFile());
        } catch (IOException e) {
            throw new ErrorCounterException("Problem with accessing starter directory", e);
        }
    }

    private Stream<String> getLinesInFile(Path file) {
        try {
            return Files.lines(file);
        } catch (IOException e) {
            throw new ErrorCounterException("Problem with opening files", e);
        }
    }

    private void writeToStatistic(String interval, Long count) {
        String outLine = interval + " Error count: " + count + "\n";
        try {
            Files.write(outFile, outLine.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new ErrorCounterException("Error occurred while trying to write to file: " + outFile, e);
        }
    }
}
