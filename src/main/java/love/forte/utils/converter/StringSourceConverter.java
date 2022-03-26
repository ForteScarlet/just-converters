package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * 以字符串作为源类型的转化器实现。
 *
 * @see StringToNumberConverter
 *
 * @author ForteScarlet
 */
public interface StringSourceConverter extends Converter {

    /**
     * 提供一个转化目标和类型并尝试进行转化。{@code source} 必须为 {@link String 字符串} 类型，
     * 否则将会抛出 {@link IllegalArgumentException} .
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T> 目标类型
     * @return 转化结果
     */
    @Override
    default <T> T convert(@NotNull Object source, @NotNull Type target) {
        if (source instanceof String) {
            return convert((String) source, target);
        }

        return ExceptionUtil.sourceIllegalArgument("java.lang.String", source);
    }

    /**
     * 提供一个转化目标和类型并尝试进行转化。{@code source} 必须为 {@link String 字符串} 类型，
     * 否则将会抛出 {@link IllegalArgumentException} .
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T> 目标类型
     * @return 转化结果
     */
    <T> T convert(@NotNull String source, @NotNull Type target);

}
