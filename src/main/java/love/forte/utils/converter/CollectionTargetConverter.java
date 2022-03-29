package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * 转化的结果目标为 {@link java.util.Collection} 的转化器。
 * 默认将会使用 {@link java.util.ArrayList} 作为最终类型。
 *
 * @author ForteScarlet
 */
public abstract class CollectionTargetConverter implements Converter {

    /**
     * 将 source 转化为目标的列表类型。{@code target} 必须保证为列表类型，且至多存在一个元素泛型。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    目标类型
     * @return converted value
     */
    @Override
    public <T> T convert(@NotNull Object source, @NotNull Type target) {
        if (target instanceof Class) {
            return convert0(source, (Class<?>) target);
        } else if (target instanceof ParameterizedType) {
            return convert0(source, (ParameterizedType) target);
        } else {
            throw ConverterExceptionUtil.targetIllegalArgument("java.util.List(by Class or ParameterizedType)", target);
        }
    }


    @SuppressWarnings("unchecked")
    protected <T> T convert0(@NotNull Object source, @NotNull Class<?> target) {
        if (Collection.class.isAssignableFrom(target)) {
            return convert(source, (Class<Collection<?>>) target, null);
        }

        throw ConverterExceptionUtil.targetIllegalArgument("java.util.List", target);

    }

    @SuppressWarnings("unchecked")
    protected <T> T convert0(@NotNull Object source, @NotNull ParameterizedType target) {
        final Type rawType = target.getRawType();
        if (rawType instanceof Class) {
            Class<?> classTarget = (Class<?>) rawType;
            if (!List.class.isAssignableFrom(classTarget)) {
                // target 中的 rawType 的泛型类型
                throw ConverterExceptionUtil.targetIllegalArgument("generic type of raw type (Class) in 'target'", "java.util.List", classTarget);
            }

            final Type[] actualTypeArguments = target.getActualTypeArguments();
            final Type genericType;
            if (actualTypeArguments == null || actualTypeArguments.length == 0) {
                genericType = null;
            } else if (actualTypeArguments.length == 1) {
                genericType = actualTypeArguments[0];
            } else {
                throw ConverterExceptionUtil.targetIllegalArgument("Actual type arguments of 'target'", "Type[(size == 1)]", target);
            }

            return convert(source, (Class<Collection<?>>) classTarget, genericType);
        }


        throw ConverterExceptionUtil.targetIllegalArgument("Raw type of 'target'", "Class<java.util.List>", target);

    }


    /**
     * 将目标对象 source 转化为目标类型的Collection的转化方法。
     *
     * @param source         转化目标
     * @param targetCollectionType 目标类型。如果使用 {@code List.class}, 则默认使用 {@link java.util.ArrayList}, 如果使用 {@code Set.class}, 则默认使用 {@link java.util.HashSet}.
     *                       如果想要使用一个自定义元素类型，需要保证此类型能够通过无参构建实例化，并且能够使用 {@link Collection#add(Object)} 来添加元素。
     * @param elementType    列表的元素类型。如果此类型为null，则由实现者自行决定所默认的元素类型。
     * @param <T>            最终的列表类型。
     * @return List value.
     */
    public abstract <T, LT extends Collection<T>> LT convert(@NotNull Object source, @NotNull Class<Collection<?>> targetCollectionType, @Nullable Type elementType);
}
