package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 提供构建一些常见或可能会用到的 {@link Type} 构建函数。
 *
 * @author ForteScarlet
 */
@SuppressWarnings("unused")
public final class TypeUtil {

    /**
     * 如果是基础数据类型的Class({@code type.isPrimitive == true}),
     * 返回此类型的包装数据类型对应的类型，否则返回默认值。
     *
     * @param type         type
     * @param defaultValue 如果不是则使用的默认值。
     * @return box type or 'default'.
     */
    public static Type primitiveToBoxOr(@NotNull Type type, boolean withVoid, Type defaultValue) {
        if (!withVoid && Void.class.equals(type)) {
            return defaultValue;
        }

        final PrimitiveType got = PrimitiveType.findByType(type);
        return got == null ? defaultValue : got.getPrimitiveType();
    }

    /**
     * 如果是基础数据类型的Class({@code type.isPrimitive == true}),
     * 返回此类型的包装数据类型对应的类型，否则返回默认值。
     * <p>
     * 默认情况下不会将 {@link Void} 也视作在内。
     *
     * @param type         type
     * @param defaultValue 如果不是则使用的默认值。
     * @return box type or 'default'.
     */
    public static Type primitiveToBoxOr(@NotNull Type type, Type defaultValue) {
        return primitiveToBoxOr(type, false, defaultValue);
    }

    /**
     * 如果是基础数据类型的Class({@code type.isPrimitive == true}),
     * 返回此类型的包装数据类型对应的类型，否则返回null。
     *
     * @param type type
     * @return box type or 'default'.
     */
    public static Type primitiveToBoxOrNull(@NotNull Type type, boolean withVoid) {
        return primitiveToBoxOr(type, withVoid, null);
    }

    /**
     * 如果是基础数据类型的Class({@code type.isPrimitive == true}),
     * 返回此类型的包装数据类型对应的类型，否则返回null。
     * <p>
     * 默认情况下不会将 {@link Void} 也视作在内。
     *
     * @param type type
     * @return box type or 'default'.
     */
    public static Type primitiveToBoxOrNull(@NotNull Type type) {
        return primitiveToBoxOr(type, false, null);
    }




    /**
     * 八个基础数据类型 和 void 的 Type 枚举。
     */
    public enum PrimitiveType {
        /**
         * @see Byte
         */
        BYTE(Byte.class, byte.class),
        /**
         * @see Short
         */
        SHORT(Short.class, short.class),
        /**
         * @see Integer
         */
        INT(Integer.class, int.class),
        /**
         * @see Long
         */
        LONG(Long.class, long.class),
        /**
         * @see Double
         */
        DOUBLE(Double.class, double.class),
        /**
         * @see Float
         */
        FLOAT(Float.class, float.class),
        /**
         * @see Character
         */
        CHAR(Character.class, char.class),
        /**
         * @see Boolean
         */
        BOOLEAN(Boolean.class, boolean.class),
        /**
         * @see Void
         */
        VOID(Void.class, void.class);

        /**
         * 此基础数据类型的封装类型。
         */
        private final Class<?> type;
        private final Class<?> primitiveType;

        PrimitiveType(Class<?> type, Class<?> primitiveType) {
            this.type = type;
            this.primitiveType = primitiveType;
        }

        @Nullable
        public static PrimitiveType findByType(Type type) {
            for (PrimitiveType value : values()) {
                if (value.type.equals(type) || value.primitiveType.equals(type)) {
                    return value;
                }
            }
            return null;
        }


        public Class<?> getType() {
            return type;
        }

        public Class<?> getPrimitiveType() {
            return primitiveType;
        }



    }


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
