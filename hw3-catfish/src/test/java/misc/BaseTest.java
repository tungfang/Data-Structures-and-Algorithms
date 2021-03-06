package misc;

import org.junit.Assert;

import java.lang.reflect.Field;

public class BaseTest {
    protected static final int SECOND = 1000;

    protected static <T> void assertEquals(T expected, T actual) {
        Assert.assertEquals(expected, actual);
    }

    protected static <T> void assertEquals(String message, T expected, T actual) {
        Assert.assertEquals(message, expected, actual);
    }

    protected static <T> T getField(Object obj, String fieldName, Class<T> expectedType) {
        return expectedType.cast(getField(obj, fieldName));
    }

    protected static Object getField(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This wrapper class allows us to define a custom hashcode for arbitrary
     * objects.
     *
     * Whoever instantiates this object MUST make sure they provide a valid
     * hashcode and inner object. That is, when given two Wrapper objects,
     * if the inner objects are equal, then their hashcodes must also be
     * equal.
     */
    protected static class Wrapper<T> {
        private T inner;
        private int hashCode;

        public Wrapper(T inner) {
            this(inner, inner == null ? 0 : inner.hashCode());
        }

        public Wrapper(T inner, int hashCode) {
            this.inner = inner;
            this.hashCode = hashCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Wrapper<?> wrapper = (Wrapper<?>) o;

            return inner != null ? inner.equals(wrapper.inner) : wrapper.inner == null;
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }
}
