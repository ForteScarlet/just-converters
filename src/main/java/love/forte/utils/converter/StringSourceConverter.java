package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * 以字符串作为源类型的转化器实现。
 *
 * @author ForteScarlet
 * @see StringToNumberConverter
 */
public interface StringSourceConverter extends Converter {

    /**
     * 提供一个转化目标和类型并尝试进行转化。{@code source} 必须为 {@link String 字符串} 类型，
     * 否则将会抛出 {@link IllegalArgumentException} .
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    目标类型
     * @return 转化结果
     * @throws NullPointerException     当参数出现null时。
     * @throws ConvertException         当出现无法进行类型转化的情况时。
     * @throws IllegalArgumentException 当 {@code source} 或 {@code target} 为不支持的实现类型时。
     * @throws ClassCastException       可能会由于种种原因而导致的类型转化异常。
     */
    @Override
    default <T> T convert(@NotNull Object source, @NotNull Type target) {
        if (source instanceof String) {
            return convert((String) source, target);
        }

        throw ConverterExceptionUtil.sourceIllegalArgument("java.lang.String", source);
    }

    /**
     * 提供一个转化目标和类型并尝试进行转化。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    目标类型
     * @return 转化结果
     * @throws NullPointerException     当参数出现null时。
     * @throws ConvertException         当出现无法进行类型转化的情况时。
     * @throws IllegalArgumentException 当 {@code source} 或 {@code target} 为不支持的实现类型时。
     * @throws ClassCastException       可能会由于种种原因而导致的类型转化异常。
     */
    <T> T convert(@NotNull String source, @NotNull Type target);

    /**
     * 提供一个转化目标和类型并尝试进行转化。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    目标类型
     * @return 转化结果
     * @throws NullPointerException     当参数出现null时。
     * @throws ConvertException         当出现无法进行类型转化的情况时。
     * @throws IllegalArgumentException 当 {@code source} 或 {@code target} 为不支持的实现类型时。
     * @throws ClassCastException       可能会由于种种原因而导致的类型转化异常。
     * @see #convert(String, Type)
     */
    @SuppressWarnings("unchecked")
    default <T> T convert(@NotNull String source, @NotNull Class<T> target) {
        return (T) convert(source, (Type) target);
    }

}
