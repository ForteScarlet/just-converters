package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 针对 {@link ParameterizedType} 的基础实现，用于提供一个 “伪” {@link ParameterizedType} 实例对象。
 *
 * @author ForteScarlet 引用类型
 *
 * @see #resolve(Class, Type, Type...)
 * @see TypeUtil
 */
public final class GenericReferenceType implements ParameterizedType {
    final Type[] actualTypeArguments;
    private final Class<?> rawType;
    private final Type ownerType;

    private GenericReferenceType(Type[] actualTypeArguments, @NotNull Class<?> rawType, @Nullable Type ownerType) {
        this.actualTypeArguments = actualTypeArguments;
        this.rawType = rawType;
        this.ownerType = ownerType;
    }


    /**
     * 尝试通过提供的参数得到一个 {@link Type} 实例。
     *
     * @param rawType             主要类型
     * @param ownerType           外层类型，可以为null
     * @param actualTypeArguments 泛型信息数组
     * @return TypeReference 实例。
     */
    public static GenericReferenceType resolve(@NotNull Class<?> rawType, @Nullable Type ownerType, Type... actualTypeArguments) {
        Objects.requireNonNull(rawType);
        return new GenericReferenceType(actualTypeArguments, rawType, ownerType);
    }


    /**
     * 简单来说，得到这个类型引用的泛型数组。
     * <p>
     * 比如你希望使其表示为 {@code List<String>}, 则泛型数组为 {@code [String.class]}.
     *
     * @return 泛型类型数组的副本。
     */
    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments.clone();
    }

    /**
     * 得到主要类型。
     * <p>
     * 也就是你想要表示的主要类型。
     *
     * @return 主要类型。
     */
    @Override
    @NotNull
    public Class<?> getRawType() {
        return rawType;
    }

    /**
     * 简单来说，得到外层类型。
     * <p>
     * 常见场景为内部类，内部类的 ownerType 即为外层的类。
     *
     * @return 外层类类，或者null。
     */
    @Override
    @Nullable
    public Type getOwnerType() {
        return ownerType;
    }


    @Override
    public String toString() {
        return rawType.toString();
    }
}
