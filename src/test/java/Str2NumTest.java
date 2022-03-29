import love.forte.utils.converter.ConverterUtil;
import love.forte.utils.converter.StringToNumberConverter;
import love.forte.utils.converter.string.StringToBooleanConverter;
import org.junit.jupiter.api.Test;

/**
 * @author ForteScarlet
 */
public class Str2NumTest {

    private static final ConverterUtil CONVERTER_UTIL = ConverterUtil.getDefault();

    @Test
    public void string2IntTest() {
        final int number = 114;
        final String numberStr = String.valueOf(number);
        assert number == StringToNumberConverter.ToInt.INSTANCE.convertNumber(numberStr);
    }

    @Test
    public void string2LongTest() {
        final long number = 1145141919810L;
        final String numberStr = String.valueOf(number);
        assert number == StringToNumberConverter.ToLong.INSTANCE.convertNumber(numberStr);
    }

    @Test
    public void string2DoubleTest() {
        final double number = 6658.4457;
        final String numberStr = String.valueOf(number);
        assert number == StringToNumberConverter.ToDouble.INSTANCE.convertNumber(numberStr);
    }

    @Test
    public void string2BooleanTest() {
        final boolean bool = true;
        final String boolStr = String.valueOf(bool);
        assert bool == StringToBooleanConverter.INSTANCE.convertBoolean(boolStr);
        assert bool == StringToBooleanConverter.INSTANCE.<Boolean>convert(boolStr, boolean.class);
        assert bool == StringToBooleanConverter.INSTANCE.<Boolean>convert(boolStr, Boolean.class);
    }


}
