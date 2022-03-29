package love.forte.utils.converter.string;

import love.forte.utils.converter.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * 将字符串转化为列表的转化器。
 * <p>
 * 当字符串符合 {@code str1,str2,str3} 的规则时，会逗号({@code ,}) 作为分隔符截取元素内容。
 * 此类的实现中，允许逗号({@code ,}) 前后分别额外存在 <b>至多一个</b> 空格字符。
 *
 * @author ForteScarlet
 */
public class StringToListConverter extends ListTargetConverter implements StringSourceConverter {
    private static final String DEFAULT_SPLIT_PATTERN = " ?, ?";
    private static final Pattern SPLIT_PATTERN = Pattern.compile(DEFAULT_SPLIT_PATTERN);
    private static final Function<String, String> DEFAULT_STR_2_STR_CONVERTER = (str) -> str;
    private static final Supplier<ConverterUtil> DEFAULT_CONVERTER_UTIL_FACTORY = ConverterUtil::getDefault;


    private final Supplier<ConverterUtil> otherTypeConverterUtilFactory;

    /**
     * 可以提供一个默认的转化器工厂，转化器会利用此工厂提供的转化器来对其他未知类型进行转化。
     * 默认情况下使用 {@link ConverterUtil#getDefault()}
     *
     * @param otherTypeConverterUtilFactory factory.
     */
    public StringToListConverter(Supplier<ConverterUtil> otherTypeConverterUtilFactory) {
        if (otherTypeConverterUtilFactory == null) {
            this.otherTypeConverterUtilFactory = DEFAULT_CONVERTER_UTIL_FACTORY;
        } else {
            this.otherTypeConverterUtilFactory = otherTypeConverterUtilFactory;
        }
    }

    public StringToListConverter() {
        this(null);
    }


    @Override
    public <T, LT extends List<T>> LT convert(@NotNull Object source, @NotNull Class<List<?>> targetListType, @Nullable Type elementType) {
        if (source instanceof String) {
            return convert((String) source, targetListType, elementType);
        }

        throw ConverterExceptionUtil.sourceIllegalArgument("java.lang.String", source);
    }

    @Override
    public <T> T convert(@NotNull String source, @NotNull Type target) {
        if (target instanceof Class) {
            return convert0(source, (Class<?>) target);
        } else if (target instanceof ParameterizedType) {
            return convert0(source, (ParameterizedType) target);
        } else {
            throw ConverterExceptionUtil.targetIllegalArgument("java.util.List(by Class or ParameterizedType)", target);
        }
    }


    /**
     * 将目标字符串转化为列表。
     * <p>
     * 列表元素 {@code elementType} 支持的类型有：
     * <ul>
     *     <li>null / Object (作为字符串处理)</li>
     *     <li>String</li>
     *     <li>Number (会通过 {@link love.forte.utils.converter.StringToNumberConverter} 下的相关转化器进行转化 )</li>
     * </ul>
     *
     * @param source         目标对象
     * @param targetListType 列表类型
     * @param elementType    元素类型
     * @return 列表结果
     */
    @SuppressWarnings("unchecked")
    public <T, LT extends List<T>> LT convert(@NotNull String source, @NotNull Class<List<?>> targetListType, @Nullable Type elementType) {
        final Function<String, T> targetConverter;
        if (null == elementType) {
            targetConverter = str2Str();
        } else if (elementType instanceof Class) {
            if (elementType.equals(String.class) || elementType.equals(Object.class)) {
                targetConverter = str2Str();
            } else {
                // not string.
                // if number?
                final TypeUtil.PrimitiveType primitiveType = TypeUtil.PrimitiveType.findByType(elementType);
                if (primitiveType != null) {
                    if (Number.class.isAssignableFrom(primitiveType.getType())) {
                        // is type of number
                        final StringToNumberConverter<?> toNumberConverter;
                        switch (primitiveType) {
                            case BYTE:
                                toNumberConverter = StringToNumberConverter.ToByte.INSTANCE;
                                break;
                            case SHORT:
                                toNumberConverter = StringToNumberConverter.ToShort.INSTANCE;
                                break;
                            case INT:
                                toNumberConverter = StringToNumberConverter.ToInt.INSTANCE;
                                break;
                            case LONG:
                                toNumberConverter = StringToNumberConverter.ToLong.INSTANCE;
                                break;
                            case DOUBLE:
                                toNumberConverter = StringToNumberConverter.ToDouble.INSTANCE;
                                break;
                            case FLOAT:
                                toNumberConverter = StringToNumberConverter.ToFloat.INSTANCE;
                                break;
                            default: toNumberConverter = null;
                        }

                        if (toNumberConverter != null) {
                            targetConverter = (str) -> (T) toNumberConverter.convertNumber(str);
                        } else {
                            // not primitive number
                            targetConverter = otherConverter(elementType);
                        }

                    } else {
                        // not number
                        targetConverter = otherConverter(elementType);
                    }
                } else {
                    // not primitive
                    targetConverter = otherConverter(elementType);
                }

            }
        } else {
            throw ConverterExceptionUtil.targetIllegalArgument("'Element type'", "", elementType.getClass());
        }

        final LT list;
        final String[] splitArray;

        if (targetListType.equals(List.class)) {
            splitArray = SPLIT_PATTERN.split(source);
            list = (LT) new ArrayList<>(splitArray.length);
        } else {
            // try to create instance.
            try {
                final Constructor<List<?>> constructor = targetListType.getConstructor();
                list = (LT) constructor.newInstance();
            } catch (ClassCastException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new ConvertException("Cannot to create instance for list type " + targetListType + ": " + e.getLocalizedMessage(), e);
            }

            splitArray = SPLIT_PATTERN.split(source);
        }


        for (String str : splitArray) {
            list.add(targetConverter.apply(str));
        }


        return list;
    }



    private <T> Function<String, T> otherConverter(Type targetType) {
        final ConverterUtil converterUtil = otherTypeConverterUtilFactory.get();
        if (converterUtil != null) {
            return (str) -> converterUtil.convert(str, targetType);
        } else {
            throw new NoSuchConverterException("java.lang.String", targetType.getTypeName());
        }
    }


    @SuppressWarnings("unchecked")
    private <T> Function<String, T> str2Str() {
        return (Function<String, T>) DEFAULT_STR_2_STR_CONVERTER;
    }
}
