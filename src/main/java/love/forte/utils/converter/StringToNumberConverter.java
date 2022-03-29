package love.forte.utils.converter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/**
 * 将字符串转化为数字的转化器。
 *
 *
 * @see ToByte
 * @see ToShort
 * @see ToInt
 * @see ToLong
 * @see ToDouble
 * @see ToFloat
 * @see ToBigDecimal
 * @see ToBigInteger
 * @see ToAtomicInteger
 * @see ToAtomicLong
 * @see ToLongAdder
 * @see ToDoubleAdder
 *
 * @author ForteScarlet
 */
@SuppressWarnings("unused")
public abstract class StringToNumberConverter<N extends Number> extends NumberConverter<N> implements StringSourceConverter {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(@NotNull String source, @NotNull Type target) {
        final Class<N> expected = getExpected();
        if (expected.equals(target)) {
            final Object converted = convertNumber(source);
            return ((Class<T>) target).cast(converted);
        }

        throw ConverterExceptionUtil.targetIllegalArgument("java.lang.Class<? extends Number>", target);
    }

    @Override
    public N convertNumber(@NotNull Object source) {
        if (source instanceof String) {
            return convertNumber((String) source);
        }

        throw ConverterExceptionUtil.sourceIllegalArgument("java.lang.String", source);
    }

    /**
     * 将字符串转化为一个目标数字类型。
     *
     * @param source 目标字符串
     * @return 转化后的数字类型。
     */
    public abstract N convertNumber(@NotNull String source);


    //region 基础实现

    private static abstract class BaseStringToNumberConverter<N extends Number> extends StringToNumberConverter<N> {
        private final Class<N> expected;

        BaseStringToNumberConverter(Class<N> expected) {
            this.expected = expected;
        }

        @NotNull
        @Override
        public Class<N> getExpected() {
            return expected;
        }
    }

    /**
     * {@link String} -> {@link Byte}
     */
    public static final class ToByte extends BaseStringToNumberConverter<Byte> {
        public static final ToByte INSTANCE = new ToByte();

        private ToByte() {
            super(Byte.class);
        }


        @Override
        public Byte convertNumber(@NotNull String source) {
            return Byte.parseByte(source);
        }
    }

    /**
     * {@link String} -> {@link Short}
     */
    public static final class ToShort extends BaseStringToNumberConverter<Short> {
        public static final ToShort INSTANCE = new ToShort();

        private ToShort() {
            super(Short.class);
        }


        @Override
        public Short convertNumber(@NotNull String source) {
            return Short.parseShort(source);
        }
    }

    /**
     * {@link String} -> {@link Integer}
     */
    public static final class ToInt extends BaseStringToNumberConverter<Integer> {
        public static final ToInt INSTANCE = new ToInt();

        private ToInt() {
            super(Integer.class);
        }


        @Override
        public Integer convertNumber(@NotNull String source) {
            return Integer.parseInt(source);
        }
    }

    /**
     * {@link String} -> {@link Long}
     */
    public static final class ToLong extends BaseStringToNumberConverter<Long> {
        public static final ToLong INSTANCE = new ToLong();

        private ToLong() {
            super(Long.class);
        }


        @Override
        public Long convertNumber(@NotNull String source) {
            return Long.parseLong(source);
        }
    }

    /**
     * {@link String} -> {@link Double}
     */
    public static final class ToDouble extends BaseStringToNumberConverter<Double> {
        public static final ToDouble INSTANCE = new ToDouble();

        private ToDouble() {
            super(Double.class);
        }


        @Override
        public Double convertNumber(@NotNull String source) {
            return Double.parseDouble(source);
        }
    }

    /**
     * {@link String} -> {@link Float}
     */
    public static final class ToFloat extends BaseStringToNumberConverter<Float> {
        public static final ToFloat INSTANCE = new ToFloat();

        private ToFloat() {
            super(Float.class);
        }


        @Override
        public Float convertNumber(@NotNull String source) {
            return Float.parseFloat(source);
        }
    }


    /**
     * {@link String} -> {@link BigDecimal}
     */
    public static final class ToBigDecimal extends BaseStringToNumberConverter<BigDecimal> {
        public static final ToBigDecimal INSTANCE = new ToBigDecimal();

        private ToBigDecimal() {
            super(BigDecimal.class);
        }


        @Override
        public BigDecimal convertNumber(@NotNull String source) {
            return new BigDecimal(source);
        }
    }


    /**
     * {@link String} -> {@link java.math.BigInteger}
     */
    public static final class ToBigInteger extends BaseStringToNumberConverter<BigInteger> {
        public static final ToBigInteger INSTANCE = new ToBigInteger();

        private ToBigInteger() {
            super(BigInteger.class);
        }


        @Override
        public BigInteger convertNumber(@NotNull String source) {
            return new BigInteger(source);
        }
    }


    /**
     * {@link String} -> {@link AtomicInteger}
     */
    public static final class ToAtomicInteger extends BaseStringToNumberConverter<AtomicInteger> {
        public static final ToAtomicInteger INSTANCE = new ToAtomicInteger();

        private ToAtomicInteger() {
            super(AtomicInteger.class);
        }


        @Override
        public AtomicInteger convertNumber(@NotNull String source) {
            return new AtomicInteger(Integer.parseInt(source));
        }
    }


    /**
     * {@link String} -> {@link AtomicLong}
     */
    public static final class ToAtomicLong extends BaseStringToNumberConverter<AtomicLong> {
        public static final ToAtomicLong INSTANCE = new ToAtomicLong();

        private ToAtomicLong() {
            super(AtomicLong.class);
        }


        @Override
        public AtomicLong convertNumber(@NotNull String source) {
            return new AtomicLong(Long.parseLong(source));
        }
    }

    /**
     * {@link String} -> {@link LongAdder}
     */
    public static final class ToLongAdder extends BaseStringToNumberConverter<LongAdder> {
        public static final ToLongAdder INSTANCE = new ToLongAdder();

        private ToLongAdder() {
            super(LongAdder.class);
        }


        @Override
        public LongAdder convertNumber(@NotNull String source) {
            final long initial = Long.parseLong(source);
            final LongAdder adder = new LongAdder();
            adder.add(initial);
            return adder;
        }
    }

    /**
     * {@link String} -> {@link DoubleAdder}
     */
    public static final class ToDoubleAdder extends BaseStringToNumberConverter<DoubleAdder> {
        public static final ToDoubleAdder INSTANCE = new ToDoubleAdder();

        private ToDoubleAdder() {
            super(DoubleAdder.class);
        }


        @Override
        public DoubleAdder convertNumber(@NotNull String source) {
            final double initial = Double.parseDouble(source);
            final DoubleAdder adder = new DoubleAdder();
            adder.add(initial);
            return adder;
        }
    }


    //endregion


}
