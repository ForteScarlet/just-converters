import love.forte.utils.converter.string.AnythingToStringConverter;
import org.junit.jupiter.api.Test;

/**
 * @author ForteScarlet
 */
public class ToStringTest {

    @Test
    public void array2Str() {
        Object obj= new int[][]{{1, 2}, {3, 4}, {5, 6, 7}};
        int[][] arrArray= new int[][]{{1, 2}, {3, 4}, {5, 6, 7}};

        final AnythingToStringConverter converter = AnythingToStringConverter.INSTANCE;
        assert converter.convertToString(arrArray).equals(converter.convertToString(obj));

    }

}
