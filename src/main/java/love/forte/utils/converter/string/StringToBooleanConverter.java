package love.forte.utils.converter.string;

import love.forte.utils.converter.BooleanConverter;
import love.forte.utils.converter.ConverterExceptionUtil;
import love.forte.utils.converter.StringSourceConverter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * 将字符串转化为 {@link Boolean} 类型的转化器。
 *
 * @author ForteScarlet
 */
public final class StringToBooleanConverter extends BooleanConverter implements StringSourceConverter {
    public static final StringToBooleanConverter INSTANCE = new StringToBooleanConverter();

    private StringToBooleanConverter() {
    }

    @Override
    public boolean convertBoolean(@NotNull Object source) {
        if (source instanceof String) {
            return convertBoolean((String) source);
        }

        throw ConverterExceptionUtil.sourceIllegalArgument("java.lang.String", source);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(@NotNull String source, @NotNull Type target) {
        if (Boolean.class.equals(target) || boolean.class.equals(target)) {
            return (T) ((Boolean) convertBoolean(source));
        }

        throw ConverterExceptionUtil.targetIllegalArgument("Class<Boolean> or Class<boolean>", target.toString());
    }


    public boolean convertBoolean(@NotNull String source) {
        return Boolean.parseBoolean(source);
    }
}
