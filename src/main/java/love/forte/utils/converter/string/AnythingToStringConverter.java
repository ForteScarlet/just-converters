package love.forte.utils.converter.string;

import love.forte.utils.converter.Converter;
import love.forte.utils.converter.ConverterExceptionUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * 将任意内容转化为字符串的转化器。如果为数组，则通过 {@code Arrays.toString},
 * 其他则会使用 {@link String#valueOf(Object)}.
 *
 * @author ForteScarlet
 */
public class AnythingToStringConverter implements Converter {
    public static final AnythingToStringConverter INSTANCE = new AnythingToStringConverter();
    private AnythingToStringConverter(){}

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(@NotNull Object source, @NotNull Type target) {
        if (String.class.equals(target)) {
            return (T) convertToString(source);
        }

        if (target instanceof ParameterizedType) {
            final ParameterizedType parameterizedTarget = (ParameterizedType) target;
            final Type[] actualTypeArguments = parameterizedTarget.getActualTypeArguments();
            if (actualTypeArguments.length <= 0) {
                throw ConverterExceptionUtil.targetIllegalArgument("'Actual type arguments in 'target'", "empty", Arrays.toString(actualTypeArguments));
            }

            final Type rawType = parameterizedTarget.getRawType();
            if (!(String.class.equals(rawType))) {
                throw ConverterExceptionUtil.targetIllegalArgument("'Raw type in 'target'", "java.lang.String", rawType.toString());
            }

            return (T) convertToString(source);
        }


        throw ConverterExceptionUtil.targetIllegalArgument("java.lang.String", target.toString());
    }


    public String convertToString(Object source) {
        if (source == null) {
            return "null";
        }

        if (source.getClass().isArray()) {
            return arrayToString(source);
        } else {
            return String.valueOf(source);
        }
    }


    private String arrayToString(Object array) {
        final int length = Array.getLength(array);
        if (length <= 0) {
            return "[]";
        }
        final StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < length; i++) {
            joiner.add(convertToString(Array.get(array, i)));
        }

        return joiner.toString();
    }

}
