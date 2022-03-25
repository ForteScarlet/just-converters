package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 提供构建一些常见或可能会用到的 {@link Type} 构建函数。
 *
 * @author ForteScarlet
 */
public final class TypeUtil {

    //region collections

    /**
     * 构建一个 {@link Collection} 为主体的类型信息。
     *
     * @param collectionType collection类型。
     * @param elementType    元素类型。
     * @return {@link GenericReferenceType}
     */
    @SuppressWarnings("rawtypes")
    public static GenericReferenceType collection(@NotNull Class<? extends Collection> collectionType, @NotNull Type elementType) {
        Objects.requireNonNull(collectionType);
        Objects.requireNonNull(elementType);
        return GenericReferenceType.resolve(collectionType, null, elementType);
    }

    /**
     * 构建一个 {@link Collection} 为主体的类型信息。
     *
     * @param elementType 元素类型。
     * @return {@link GenericReferenceType}
     */
    public static GenericReferenceType collection(@NotNull Type elementType) {
        return collection(Collection.class, elementType);
    }

    /**
     * 构建一个 {@link List} 为主体的类型信息。
     *
     * @param listType    list类型。
     * @param elementType 元素类型。
     * @return {@link GenericReferenceType}
     */
    @SuppressWarnings("rawtypes")
    public static GenericReferenceType list(@NotNull Class<? extends List> listType, @NotNull Type elementType) {
        return collection(listType, elementType);
    }

    /**
     * 构建一个 {@link List} 为主体的类型信息。
     *
     * @param elementType 元素类型。
     * @return {@link GenericReferenceType}
     */
    public static GenericReferenceType list(@NotNull Type elementType) {
        return list(List.class, elementType);
    }

    /**
     * 构建一个 {@link Set} 为主体的类型信息。
     *
     * @param setType     Set类型。
     * @param elementType 元素类型。
     * @return {@link GenericReferenceType}
     */
    @SuppressWarnings("rawtypes")
    public static GenericReferenceType set(@NotNull Class<? extends Set> setType, @NotNull Type elementType) {
        return collection(setType, elementType);
    }

    /**
     * 构建一个 {@link Set} 为主体的类型信息。
     *
     * @param elementType 元素类型。
     * @return {@link GenericReferenceType}
     */
    public static GenericReferenceType set(@NotNull Type elementType) {
        return set(Set.class, elementType);
    }
    //endregion


    //region map

    /**
     * 构建一个 {@link Map} 为主体的类型信息。
     *
     * @param mapType   list类型。
     * @param keyType   键类型。
     * @param valueType 值类型。
     * @return {@link GenericReferenceType}
     */
    @SuppressWarnings("rawtypes")
    public static GenericReferenceType map(@NotNull Class<? extends Map> mapType, @NotNull Type keyType, @NotNull Type valueType) {
        Objects.requireNonNull(mapType);
        Objects.requireNonNull(keyType);
        Objects.requireNonNull(valueType);
        return GenericReferenceType.resolve(mapType, null, keyType, valueType);
    }

    /**
     * 构建一个 {@link Map} 为主体的类型信息。
     *
     * @param keyType   键类型。
     * @param valueType 值类型。
     * @return {@link GenericReferenceType}
     */
    public static GenericReferenceType map(@NotNull Type keyType, @NotNull Type valueType) {
        Objects.requireNonNull(Map.class);
        Objects.requireNonNull(keyType);
        Objects.requireNonNull(valueType);
        return GenericReferenceType.resolve(Map.class, null, keyType, valueType);
    }
    //endregion


    //region array
    /**
     * 提供一个数组的泛型类型，得到对应的 {@link GenericArrayType} 实例。
     *
     * @param componentType 数组元素类型
     * @return {@link GenericArrayType}
     */
    public static GenericArrayType array(@NotNull Type componentType) {
        Objects.requireNonNull(componentType);
        return new GenericArrayTypeImpl(componentType);
    }
    //endregion


}
