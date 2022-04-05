package love.forte.utils.converter.string;

import love.forte.utils.converter.ConvertException;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 将字符串转化为与时间相关的类型的转化器。
 * <p>
 * 相关的可转化内容有：
 * <ul>
 *     <li>{@link java.util.concurrent.TimeUnit}</li>
 *     <li>{@link java.util.Date}</li>
 *     <li>{@link java.time.LocalDate}</li>
 *     <li>{@link java.time.LocalTime}</li>
 *     <li>{@link java.time.LocalDateTime}</li>
 *     <li>{@link java.time.OffsetDateTime}</li>
 *     <li>{@link java.time.ZonedDateTime}</li>
 *     <li>{@link java.time.Clock}</li>
 *     <li>{@link java.time.Instant}</li>
 *     <li>{@link java.time.OffsetTime}</li>
 *     <li>{@link java.time.MonthDay}</li>
 *     <li>{@link java.time.YearMonth}</li>
 *     <li>{@link java.time.Duration}</li>
 *     <li>{@link java.time.Period}</li>
 *     <li>{@link java.time.Year}</li>
 *     <li>{@link java.time.Month}</li>
 *     <li>{@link java.time.YearMonth}</li>
 *     <li>{@link java.time.DayOfWeek}</li>
 * </ul>
 *
 * @author ForteScarlet
 */
public class StringToTimeConverter extends StringSourceClassTargetConverter {

    // todo

    private DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    private final Map<Class<?>, ThrowableFunction<String, Object>> convertersMap;

    private StringToTimeConverter() {


        Map<Class<?>, ThrowableFunction<String, Object>> convertersMap = new HashMap<>();
        convertersMap.put(Date.class, this::convertToDate);
        convertersMap.put(TimeUnit.class, this::convertToTimeUnit);


        this.convertersMap = convertersMap;
    }

    @Override
    protected Object convertStringByClass(@NotNull String source, @NotNull Class<?> target) {
        final ThrowableFunction<String, Object> converter = convertersMap.get(target);
        if (converter == null) {
            throw new ConvertException("Convert type not supported: " + target);
        }


        try {
            return converter.apply(source);
        } catch (Exception e) {
            throw new ConvertException("Convert failed.", e);
        }
    }


    /**
     * {@link String} 转化为 {@link TimeUnit}.
     *
     * @param source string source
     * @return time unit.
     * @throws IllegalArgumentException 名称未找到
     */
    public TimeUnit convertToTimeUnit(String source) {
        return TimeUnit.valueOf(source);
    }

    /**
     * {@link String} 转化为 {@link Date}.
     *
     * @param source string source
     * @return date
     * @throws ParseException 日期转化异常。
     */
    public Date convertToDate(String source) throws ParseException {
        return dateFormat.parse(source);
    }

    //region Local date time
    /**
     * {@link String} 转化为 {@link LocalDateTime}.
     *
     * @param source source string.
     * @return local date time.
     */
    public LocalDateTime convertToLocalDateTime(String source) {
        return LocalDateTime.parse(source, dateTimeFormatter);
    }

    /**
     * {@link String} 转化为 {@link LocalDate}.
     *
     * @param source source string.
     * @return local date.
     */
    public LocalDate convertToLocalDate(String source) {
        return LocalDate.parse(source, dateTimeFormatter);
    }

    /**
     * {@link String} 转化为 {@link LocalTime}.
     *
     * @param source source string.
     * @return local time.
     */
    public LocalTime convertToLocalTime(String source) {
        return LocalTime.parse(source, dateTimeFormatter);
    }
    //endregion


    @FunctionalInterface
    private interface ThrowableFunction<T, R> {
        /**
         * 执行
         *
         * @param t param
         * @return result
         * @throws Exception any exception
         */
        R apply(T t) throws Exception;
    }
}
