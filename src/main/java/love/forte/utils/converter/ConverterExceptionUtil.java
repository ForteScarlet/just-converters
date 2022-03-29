package love.forte.utils.converter;

import org.jetbrains.annotations.ApiStatus;

/**
 * 内部使用的异常构建工具。
 *
 * @author ForteScarlet
 */
@SuppressWarnings("unused")
@ApiStatus.Internal
public final class ConverterExceptionUtil {

    public static IllegalArgumentException sourceIllegalArgument(String mustBe, String butIs) {
        return targetIllegalArgument("The type of 'source'", mustBe, butIs);
    }

    public static IllegalArgumentException sourceIllegalArgument(String mustBe, Object butIs) {
        return sourceIllegalArgument(mustBe, butIs.getClass().toString());
    }

    public static IllegalArgumentException targetIllegalArgument(String mustBe, String butIs) {
        return targetIllegalArgument("The type of 'target'", mustBe, butIs);
    }

    public static IllegalArgumentException targetIllegalArgument(String mustBe, Object butIs) {
        return targetIllegalArgument(mustBe, butIs.getClass().toString());
    }

    public static IllegalArgumentException sourceIllegalArgument(String who, String mustBe, String butIs) {
        return new IllegalArgumentException(who + " must be " + mustBe + ", but it is " + butIs);
    }

    public static IllegalArgumentException sourceIllegalArgument(String who, String mustBe, Object butIs) {
        return sourceIllegalArgument(who, mustBe, butIs.getClass().toString());
    }

    public static IllegalArgumentException targetIllegalArgument(String who, String mustBe, String butIs) {
        return new IllegalArgumentException(who + " must be " + mustBe + ", but it is " + butIs);
    }

    public static IllegalArgumentException targetIllegalArgument(String who, String mustBe, Object butIs) {
        return targetIllegalArgument(who, mustBe, butIs.getClass().toString());
    }

}
