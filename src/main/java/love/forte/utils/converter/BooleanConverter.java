package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

/**
 * 以 {@link Boolean} 类型为目标的转化器。
 *
 * @author ForteScarlet
 */
public abstract class BooleanConverter extends ClassTargetConverter {
    @Override
    protected Object convert(@NotNull Object source, @NotNull Class<?> target) {
        if (Boolean.class.equals(target) || boolean.class.equals(target)) {
            return convertBoolean(source);
        }

        return ExceptionUtil.targetIllegalArgument("Class<java.lang.Boolean> or Class<java.lang.boolean>", target.toString());
    }

    /**
     * 将目标转化为 {@link Boolean} 类型。
     *
     * @param source source
     * @return boolean
     */
    public abstract boolean convertBoolean(@NotNull Object source);
}
