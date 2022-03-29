import love.forte.utils.converter.ConverterUtil;
import love.forte.utils.converter.TypeUtil;
import love.forte.utils.converter.string.StringToListConverter;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author ForteScarlet
 */
public class Str2ListTest {
    private static final ConverterUtil CONVERTER_UTIL = ConverterUtil.getDefault();
    private static final StringToListConverter STRING_TO_LIST_CONVERTER = new StringToListConverter();

    @Test
    public void str2StrList() {
        final String str = "1,2,3,4 , 5, 6 ,7";
        final List<String> list = STRING_TO_LIST_CONVERTER.convert(str, TypeUtil.list(String.class));

        System.out.println(list);
        assert list.size() == 7;
        for (int i = 0; i < list.size(); i++) {
            assert String.valueOf(i + 1).equals(list.get(i));
        }

    }

    @Test
    public void str2IntList() {
        final String str = "1,2,3,4 , 5, 6 ,7";
        final List<Integer> list = STRING_TO_LIST_CONVERTER.convert(str, TypeUtil.list(int.class));

        System.out.println(list);
        assert list.size() == 7;
        for (int i = 0; i < list.size(); i++) {
            assert (i + 1) == list.get(i);
        }
    }


}
