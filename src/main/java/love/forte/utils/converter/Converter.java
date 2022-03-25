package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * 一个<b>转化器</b>。
 * <p>
 * 转化器是一个用于将一个目标对象转化为一个目标类型的处理器接口。
 *
 * @author ForteScarlet
 */
public interface Converter {


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
     */
    <T> T convert(@NotNull Object source, @NotNull Type target);

}
