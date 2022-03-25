package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

/**
 * 基础的 {@link ConverterUtil} 实现，提供若干默认转化器。
 * <p>
 * {@link SimpleConverterUtil} 在进行转化时会优先使用 {@code source} 的 {@code getClass()} 的
 * <b>类型名称</b>（即 {@code getTypeName()} ）作为类型的检测目标。当无匹配时才会考虑检测继承关系。
 * <p>
 * note: 此实现不是线程安全的。
 *
 * @author ForteScarlet
 */
public final class SimpleConverterUtil implements ConverterUtil {
    /**
     * key: 源类型
     * value: 目标类型
     */
    private final Map<String, Map<String, Converter>> converters;

    private SimpleConverterUtil(Map<String, Map<String, Converter>> converters) {
        this.converters = converters;
    }

    /**
     * 如果是 {@link java.lang.reflect.ParameterizedType} 类型，则会使用其 {@link ParameterizedType#getRawType()} 进行判断。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T> result type
     * @return converted result
     */
    @Override
    public <T> T convert(@NotNull Object source, @NotNull Type target) {
        // TODO
        return null;
    }

    @Nullable
    public Converter getConverter(String sourceType, String targetType) {
        return converters.getOrDefault(sourceType, Collections.emptyMap()).get(targetType);
    }

    @Nullable
    public Converter getConverter(Type sourceType, Type targetType) {
        return converters.getOrDefault(sourceType.getTypeName(), Collections.emptyMap()).get(targetType.getTypeName());
    }
}
