package love.forte.utils.converter.string;

import love.forte.utils.converter.ClassTargetConverter;
import love.forte.utils.converter.ConverterExceptionUtil;
import love.forte.utils.converter.StringSourceConverter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 以字符串作为转化对象、以一个具体的类型作为转化目标类型的转化器。
 *
 * @author ForteScarlet
 */
public abstract class StringSourceClassTargetConverter extends ClassTargetConverter implements StringSourceConverter {

    @Override
    protected Object convertByClass(@NotNull Object source, @NotNull Class<?> target) {
        if (source instanceof String) {
            return convertStringByClass((String) source, target);
        }

        throw ConverterExceptionUtil.sourceIllegalArgument("java.lang.String", source.getClass().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(@NotNull String source, @NotNull Type target) {
        if (target instanceof Class) {
            return convertString(source, (Class<T>) target);
        } else if (target instanceof ParameterizedType) {
            ParameterizedType parameterizedTarget = (ParameterizedType) target;
            final Type[] arguments = parameterizedTarget.getActualTypeArguments();
            if (arguments.length > 0) {
                throw ConverterExceptionUtil.targetIllegalArgument("ActualTypeArguments in parameterized target","empty", arguments.length);
            }

            final Type rawType = parameterizedTarget.getRawType();
            return convert(source, rawType);
        }


        throw ConverterExceptionUtil.targetIllegalArgument("Class or ParameterizedType", target.getClass().toString());
    }


    /**
     * 将字符串转化为目标类型。
     *
     * @param source 目标字符串
     * @param target 目标类型
     * @param <T>    目标类型
     * @return 转化结果
     * @throws love.forte.utils.converter.ConvertException 转化出现异常
     * @throws ClassCastException                          类型转化异常
     */
    public <T> T convertString(@NotNull String source, @NotNull Class<T> target) {
        return target.cast(convertStringByClass(source, target));
    }


    /**
     * 将一个目标对象转化为指定的具体类型。
     *
     * @param source 目标对象
     * @param target 转化目标类型
     * @return 转化结果，会通过 Class.cast 转化其类型。
     */
    protected abstract Object convertStringByClass(@NotNull String source, @NotNull Class<?> target);

}
