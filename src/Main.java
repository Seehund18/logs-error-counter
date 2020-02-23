import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class Main {
    // Папка с логами
    private static Path logsDir = Paths.get("logs/");

    // Выходной файл со статистикой
    private static Path statisticFile = Paths.get("Statistics.txt");

    // Регулярное выражение для строки в логах, которая содержит ошибку. В моём случае такая строка содержит "ERROR"
    // с датой и временем перед ней
    private static String errorPattern = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3} ERROR.*";

    // Регулярное выражение для формата даты в логах
    private static String datePattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}";

    // Формат времени
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Маппер времени в интервал
    private static TimeToIntervalMapper resolver = new HourIntervalMapper();

    public static void main(String[] args) {

        LogsDateTime dateTime = new LogsDateTime(datePattern, formatter);

        ErrorCounter counter = new ErrorCounter(logsDir, statisticFile, resolver, errorPattern, dateTime);
        counter.countErrors();
    }
}
