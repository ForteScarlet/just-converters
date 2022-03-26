package love.forte.utils.converter;

import java.util.NoSuchElementException;

/**
 * 当没有找到合适的转化器的时候则会抛出此异常。
 *
 * @author ForteScarlet
 */
public class NoSuchConverterException extends NoSuchElementException {
    public NoSuchConverterException(String message) {
        super(message);
    }

    public NoSuchConverterException(String fromType, String toType) {
        this("from '" + fromType + "' to '" + toType + "'");
    }
}
