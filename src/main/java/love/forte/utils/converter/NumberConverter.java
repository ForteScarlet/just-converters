package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

/**
 * 转化目标为数字类型的转化器。
 *
 * @author ForteScarlet
 */
public abstract class NumberConverter<N extends Number> extends ClassTargetConverter {

    @Override
    protected Object convert(@NotNull Object source, @NotNull Class<?> target) {
        final Class<N> expected = getExpected();

        if (expected.equals(target)) {
            return convertNumber(source);
        }

        return ConverterExceptionUtil.targetIllegalArgument(expected.toString(), source);
    }


    /**
     * 得到预期的数字类型。用于进行参数验证。
     *
     * @return Class
     */
    @NotNull
    protected abstract Class<N> getExpected();


    /**
     * 将源实例转化为一个目标数字类型。
     *
     * @param source 目标字符串
     * @return 转化后的数字类型。
     */
    public abstract N convertNumber(@NotNull Object source);
}
