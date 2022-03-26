package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * 基础的 {@link ConverterUtil} 实现，提供若干默认转化器。
 * <p>
 * {@link SimpleConverterUtil} 在进行转化时会优先使用 {@code source} 的 {@code getClass()} 的
 * <b>类型名称</b>（即 {@code getTypeName()} ）作为类型的检测目标。当无匹配时才会考虑检测继承关系。
 * <p>
 * note: 此实现不是线程安全的。
 *
 * @author ForteScarlet
 */
public final class SimpleConverterUtil implements ConverterUtil {
    private final PrecisionConverterLocator precisionConverterLocator;
    private final CalculationConverterLocator calculationConverterLocator;

    private SimpleConverterUtil(PrecisionConverterLocator precisionConverterLocator, CalculationConverterLocator calculationConverterLocator) {
        this.precisionConverterLocator = precisionConverterLocator;
        this.calculationConverterLocator = calculationConverterLocator;
    }


    public static SimpleConverterUtil getInstance(PrecisionConverterLocator precisionConverterLocator, CalculationConverterLocator calculationConverterLocator) {
        return new SimpleConverterUtil(precisionConverterLocator, calculationConverterLocator);
    }


    public static SimpleConverterUtil getInstance() {
        return getInstance(new PrecisionConverterLocator(), new CalculationConverterLocator());
    }


    /**
     * 如果是 {@link java.lang.reflect.ParameterizedType} 类型，则会使用其 {@link ParameterizedType#getRawType()} 进行判断。
     *
     * @param source 预计被转化的目标对象
     * @param target 预计被转化为的目标类型。
     * @param <T>    result type
     * @return converted result
     */
    @Override
    public <T> T convert(@NotNull Object source, @NotNull Type target) {
        // 优先通过精准定位器查询。
        Converter converter = precisionConverterLocator.matchConverter(source, target);
        if (converter == null) {
            converter = calculationConverterLocator.matchConverter(source, target);
        }
        if (converter == null) {
            throw new NoSuchConverterException(source.getClass().getName(), target.getTypeName());
        }

        return converter.convert(source, target);
    }


    public PrecisionConverterLocator getPrecisionConverterLocator() {
        return precisionConverterLocator;
    }

    public CalculationConverterLocator getCalculationConverterLocator() {
        return calculationConverterLocator;
    }

    /**
     * 转化定位器。定位器有两个实现类：精准的定位器 Precision，计算的定位器 Calculation
     */
    public static abstract class ConverterLocator {
        private ConverterLocator() {
        }

        /**
         * 检测目标是否为可转化目标。
         *
         * @param source     目标对象
         * @param targetType 目标类型
         * @return 是否可以转化
         */
        @Nullable
        public abstract Converter matchConverter(Object source, Type targetType);


    }

    /**
     * 精准的定位器。
     */
    public static class PrecisionConverterLocator extends ConverterLocator {
        private final Map<String, Map<String, Converter>> coordinates;
        private final Supplier<Map<String, Converter>> mapSupplier;

        public PrecisionConverterLocator(Map<String, Map<String, Converter>> coordinates, Supplier<Map<String, Converter>> mapSupplier) {
            this.coordinates = coordinates;
            this.mapSupplier = mapSupplier;
        }

        public PrecisionConverterLocator() {
            this(new LinkedHashMap<>(), LinkedHashMap::new);
        }

        @Override
        public @Nullable Converter matchConverter(Object source, Type targetType) {
            return coordinates.getOrDefault(source.getClass().getName(), Collections.emptyMap())
                    .get(targetType.getTypeName());
        }

        public Converter set(String sourceName, String targetName, Converter converter) {
            final Map<String, Converter> map = coordinates.computeIfAbsent(sourceName, k -> mapSupplier.get());
            return map.put(targetName, converter);
        }

        public Converter remove(String sourceName, String targetName) {
            final Map<String, Converter> converterMap = coordinates.get(sourceName);
            if (converterMap == null) {
                return null;
            }

            return converterMap.remove(targetName);
        }
    }

    /**
     * 计算的定位器。
     */
    public static class CalculationConverterLocator extends ConverterLocator {
        private final Map<String, ConverterFactory> factories;

        public CalculationConverterLocator(Map<String, ConverterFactory> factories) {
            this.factories = factories;
        }

        public CalculationConverterLocator() {
            this(new LinkedHashMap<>());
        }

        @Override
        public @Nullable Converter matchConverter(Object source, Type targetType) {
            final Collection<ConverterFactory> values = factories.values();
            for (ConverterFactory factory : values) {
                if (factory.checker.test(source, targetType)) {
                    return factory.converter;
                }
            }

            return null;
        }

        public Converter set(String id, Converter converter, BiPredicate<Object, Type> checker) {
            final ConverterFactory factory = new ConverterFactory(converter, checker);
            final ConverterFactory foundFactory = factories.put(id, factory);
            return foundFactory == null ? null : foundFactory.converter;
        }

        public Converter remove(String id) {
            final ConverterFactory removed = factories.remove(id);
            return removed == null ? null : removed.converter;

        }

        private static class ConverterFactory {
            private final Converter converter;
            private final BiPredicate<Object, Type> checker;

            private ConverterFactory(Converter converter, BiPredicate<Object, Type> checker) {
                this.converter = converter;
                this.checker = checker;
            }
        }
    }


}
