package coin.gaming.demo.common;

import coin.gaming.demo.exception.InternalServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.util.Collection;
import java.util.Map;


public final class Utils {

    private Utils() {}

    /**
     * Generates a JSON from a Java Object.
     *
     * @param obj
     *
     * @return generated JSON as a String.
     */
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper om = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
            return om.writeValueAsString(obj);
        } catch (Exception e) {
            throw new InternalServerErrorException("JSON converting failed.", e);
        }
    }

    /**
     * Calculate seconds between start and end.
     *
     * @param start in milliseconds (System.currentTimeMillis())
     * @param end   in milliseconds (System.currentTimeMillis())
     *
     * @return
     */
    public static long calSeconds(long start, long end) {
        return (end - start) / 1000;
    }

    /**
     * @param collection
     *
     * @return safe against NPE size of the given collection
     */
    public static int safeSize(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * @param map
     *
     * @return safe against NPE size of the given map.
     */
    public static int safeSize(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }
}
