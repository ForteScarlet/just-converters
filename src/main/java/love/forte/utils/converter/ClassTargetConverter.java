package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * 以 {@link Class} 类型作为目标的转化器。也就是不需要解析泛型内容的转化器。
 * <p>
 * 当 {@link #convert(Object, Type)} 提供的 {@code Type target} 类型不是 {@link Class} 的时候，
 * 将会抛出 {@link ConvertException}.
 *
 * @author ForteScarlet
 */
public abstract class ClassTargetConverter implements Converter {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(@NotNull Object source, @NotNull Type target) {
        if (target instanceof Class) {
            Class<T> targetClass = (Class<T>) target;
            return targetClass.cast(target);
        }

        throw ConverterExceptionUtil.targetIllegalArgument("java.lang.Class", target);
    }

    /**
     * 根据提供的参数和目标类型，将 {@code source} 转化为 {@code target} 所约束类型。
     * 返回值类型需要与 {@code target} 相对应，{@link ClassTargetConverter} 会在此函数之后完成后，通过
     *
     * @param source 期望被转化的目标
     * @param target 期望的目标类型
     * @return 转化结果。会通过 {@link Class#cast} 进行转化。
     */
    protected abstract Object convert(@NotNull Object source, @NotNull Class<?> target);


}
