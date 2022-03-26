package love.forte.utils.converter;

import org.jetbrains.annotations.Contract;

/**
 * @author ForteScarlet
 */
public final class ExceptionUtil {

    @Contract("_, _ -> fail")
    public static <T> T sourceIllegalArgument(String mustBe, String butIs) {
        throw new IllegalArgumentException("The type of 'source' must be " + mustBe + ", but it is " + butIs);
    }

    @Contract("_, _ -> fail")
    public static <T> T sourceIllegalArgument(String mustBe, Object butIs) {
        return sourceIllegalArgument(mustBe, butIs.getClass().toString());
    }

    @Contract("_, _ -> fail")
    public static <T> T targetIllegalArgument(String mustBe, String butIs) {
        throw new IllegalArgumentException("The type of 'target' must be " + mustBe + ", but it is " + butIs);
    }

    @Contract("_, _ -> fail")
    public static <T> T targetIllegalArgument(String mustBe, Object butIs) {
        return targetIllegalArgument(mustBe, butIs.getClass().toString());
    }

}
