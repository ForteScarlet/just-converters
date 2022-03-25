package love.forte.utils.converter;

/**
 * 类型转化异常。
 *
 * @author ForteScarlet
 */
public class ConvertException extends IllegalArgumentException {
    public ConvertException() {
    }

    public ConvertException(String s) {
        super(s);
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }
}
