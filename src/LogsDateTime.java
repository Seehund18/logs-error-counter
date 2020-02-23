import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, содержащий в себе данные о дате в логах:
 * 1) скомпилированное регулярное выражение для выделения даты из строки;
 * 2) формат этой даты, который используется для преобразования в объект класса LocalDateTime
 */
public class LogsDateTime {
    private Pattern dateTimePattern;
    private DateTimeFormatter formatter;

    public LogsDateTime(String datePattern, DateTimeFormatter formatter) {
        this.dateTimePattern = Pattern.compile(datePattern);
        this.formatter = formatter;
    }

    public Pattern getDatePattern() {
        return dateTimePattern;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    /**
     * Выделяет из строки дату и преобразует его в LocalDateTime
     * @param line
     * @return
     */
    public LocalDateTime parseDateTime(String line) {
        String dateTime = null;
        Matcher matcher = dateTimePattern.matcher(line);
        if (matcher.find()) {
            dateTime = matcher.group();
        }

        if (dateTime == null || dateTime.trim().equals("")) {
            throw new IllegalArgumentException("Given line doesn't contain given dateTime pattern");
        }
        return LocalDateTime.parse(dateTime, formatter);
    }
}
