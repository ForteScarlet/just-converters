import love.forte.utils.converter.ConverterUtil;
import love.forte.utils.converter.string.StringToArrayConverter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author ForteScarlet
 */
public class Str2ArrayTest {
    private static final ConverterUtil CONVERTER_UTIL = ConverterUtil.getDefault();
    private static final StringToArrayConverter STRING_TO_ARRAY_CONVERTER = new StringToArrayConverter();

    @Test
    public void str2StrArray() {
        final String str = "1,2,3,4 , 5, 6 ,7";
        final String[] array = STRING_TO_ARRAY_CONVERTER.convert(str, String[].class);

        System.out.println(Arrays.toString(array));
        assert array.length == 7;
        for (int i = 0; i < array.length; i++) {
            assert String.valueOf(i + 1).equals(array[i]);
        }

    }

    @Test
    public void str2IntArray() {
        final String str = "1,2,3,4 , 5, 6 ,7";
        final Integer[] array = STRING_TO_ARRAY_CONVERTER.convert(str, Integer[].class);

        System.out.println(Arrays.toString(array));
        assert array.length == 7;
        for (int i = 0; i < array.length; i++) {
            assert (i + 1) == array[i];
        }
    }

    @Test
    public void str2DoubleArray() {
        final String str = "1,2,3,4 , 5.5, 6.6 ,7";
        final Double[] array = STRING_TO_ARRAY_CONVERTER.convert(str, Double[].class);

        System.out.println(Arrays.toString(array));
        assert array.length == 7;
    }


}
