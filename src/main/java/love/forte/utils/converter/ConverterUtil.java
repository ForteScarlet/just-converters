package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * 很好理解的"转化工具"类型。
 * <p>
 * 转化工具相当于一个针对很多 {@link Converter 转化器} 的“调度场”.
 *
 * @author ForteScarlet
 */
public interface ConverterUtil {


    /**
     * 得到一个全局默认使用的 {@link ConverterUtil} 实例。
     * @return {@link ConverterUtil} instance.
     */
    static ConverterUtil getDefault() {
        return DefaultInstance.DEFAULT_INSTANCE;
    }

    /**
     * 将一个目标对象 source 转化为期望的目标类型 target。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    结果类型。类型应当与参数 target 所表示的类型一致，否则可能会导致类型转化异常。
     * @return 转化结果。
     * @throws NullPointerException     当参数出现null时。
     * @throws ConvertException         当出现无法进行类型转化的情况时。
     * @throws IllegalArgumentException 当 target 为不支持的实现类型时。
     * @throws NoSuchConverterException 当当前转化工具内部没有适合的 {@link Converter 转化器} 时。
     * @see Converter
     */
    <T> T convert(@NotNull Object source, @NotNull Type target);

    /**
     * 将一个目标对象 source 转化为期望的目标类型 target。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    结果类型。类型应当与参数 target 所表示的类型一致，否则可能会导致类型转化异常。
     * @return 转化结果。
     * @throws NullPointerException     当参数出现null时。
     * @throws ConvertException         当出现无法进行类型转化的情况时。
     * @throws IllegalArgumentException 当 target 为不支持的实现类型时。
     * @throws NoSuchConverterException 当当前转化工具内部没有适合的 {@link Converter 转化器} 时。
     * @see Converter
     */
    default <T> T convert(@NotNull Object source, @NotNull Class<T> target) {
        return convert(source, (Type) target);
    }





}

/**
 * Default instance of {@link ConverterUtil}.
 */
class DefaultInstance {
    static final ConverterUtil DEFAULT_INSTANCE;
    static {
        // TODO include default converters
        DEFAULT_INSTANCE = SimpleConverterUtil.getInstance();
    }
}