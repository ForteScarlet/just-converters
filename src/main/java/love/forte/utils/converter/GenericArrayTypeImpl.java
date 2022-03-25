package love.forte.utils.converter;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * 针对 {@link GenericArrayType} 的基础实现。
 *
 * @author ForteScarlet
 *
 * @see TypeUtil#array(Type)
 */
public final class GenericArrayTypeImpl implements GenericArrayType {
    private final Type genericComponentType;

    GenericArrayTypeImpl(Type genericComponentType) {
        this.genericComponentType = genericComponentType;
    }

    @Override
    public Type getGenericComponentType() {
        return genericComponentType;
    }


}
