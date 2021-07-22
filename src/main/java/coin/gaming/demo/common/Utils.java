package coin.gaming.demo.common;

import coin.gaming.demo.exception.InternalServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.util.Collection;
import java.util.Map;


public final class Utils {

    public Utils getInstance() {
        return new Utils();
    }

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

    public static long calSeconds(long start, long end) {
        return (end - start) / 1000;
    }

    public static int safeSize(Collection<?> obj) {
        return obj == null ? 0 : obj.size();
    }

    public static int safeSize(Map<?, ?> obj) {
        return obj == null ? 0 : obj.size();
    }
}
