import java.time.LocalDateTime;

/**
 *  Интерфейс для мапперов, которые отражают время в определённый интервал.
 *  Конкретные интервалы определяются имплементациями данного интерфейса
 */
public interface TimeToIntervalMapper {

    /**
     * Отражает время в определённый временной интервал
     * @param dateTime
     * @return
     */
    String mapTimeToInterval(LocalDateTime dateTime);
}
