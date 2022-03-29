package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 *
 * 作为元素容器为目标的转化器，例如 {@link java.util.Collection} 或 数组。
 *
 * @author ForteScarlet
 */
public abstract class ElementContainerTargetConverter implements Converter {

    @Override
    public <T> T convert(@NotNull Object source, @NotNull Type target) {
        return null;
    }
}
