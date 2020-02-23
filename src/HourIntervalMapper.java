import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Реализация временного маппера, который отражает время в часовой интервал
 */
public class HourIntervalMapper implements TimeToIntervalMapper {

    /**
     * Отражает время в часовые промежутки. Пример,
     * 2019-01-25 12:36:00 -> 2019-01-25 12-13
     * 2019-02-21 13:00:00 -> 2019-02-21 13-14
     * 2020-04-19 23:00:00 -> 2020-04-19 23-0
     * @param dateTime
     * @return
     */
    @Override
    public String mapTimeToInterval(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int nextHour = (hour == 23) ? 0 : hour + 1;

        LocalDate date = dateTime.toLocalDate();
        return date + " " + hour + "-" + nextHour;
    }
}
