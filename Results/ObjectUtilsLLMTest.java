package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.exception.CloneFailedException;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

public class ObjectUtilsLLMTest {

    // --- Null / NotNull Tests ---

    @Test
    void testAllNotNull() {
        assertTrue(ObjectUtils.allNotNull(), "Empty varargs should be true");
        assertTrue(ObjectUtils.allNotNull("a", "b"), "No nulls should be true");
        assertFalse(ObjectUtils.allNotNull((Object) null), "Single null should be false");
        assertFalse(ObjectUtils.allNotNull("a", null, "c"), "Mixed null should be false");
        assertFalse(ObjectUtils.allNotNull((Object[]) null), "Null array should be false");
    }

    @Test
    void testAllNull() {
        assertTrue(ObjectUtils.allNull(), "Empty varargs should be true");
        assertTrue(ObjectUtils.allNull(null, null), "All nulls should be true");
        assertFalse(ObjectUtils.allNull("a", null), "Mixed should be false");
        assertTrue(ObjectUtils.allNull((Object[]) null), "Null array should be true");
    }

    @Test
    void testAnyNotNull() {
        assertFalse(ObjectUtils.anyNotNull(), "Empty varargs should be false");
        assertTrue(ObjectUtils.anyNotNull(null, "a"), "One non-null should be true");
        assertFalse(ObjectUtils.anyNotNull(null, null), "All nulls should be false");
    }

    @Test
    void testAnyNull() {
        assertTrue(ObjectUtils.anyNull((Object[]) null), "Null array should be true");
        assertTrue(ObjectUtils.anyNull(null, "a"), "One null should be true");
        assertFalse(ObjectUtils.anyNull("a", "b"), "No nulls should be false");
    }

    // --- Cloning Tests ---

    static class PublicClone implements Cloneable {
        @Override
        public Object clone() throws CloneNotSupportedException { return super.clone(); }
    }

    static class PrivateClone implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException { return super.clone(); }
    }

    @Test
    void testClone() {
        assertNull(ObjectUtils.clone(null));
        assertNull(ObjectUtils.clone("NotCloneable")); 
        
        PublicClone pc = new PublicClone();
        assertNotNull(ObjectUtils.clone(pc));
        assertNotSame(pc, ObjectUtils.clone(pc));

        int[] primArray = {1, 2};
        assertArrayEquals(primArray, ObjectUtils.clone(primArray));
        
        String[] objArray = {"a"};
        assertArrayEquals(objArray, ObjectUtils.clone(objArray));
    }

    @Test
    void testCloneFailure() {
        PrivateClone privateC = new PrivateClone();
        assertThrows(CloneFailedException.class, () -> ObjectUtils.clone(privateC));
    }

    @Test
    void testCloneIfPossible() {
        String s = "test";
        assertSame(s, ObjectUtils.cloneIfPossible(s));
        PublicClone pc = new PublicClone();
        assertNotSame(pc, ObjectUtils.cloneIfPossible(pc));
    }

    // --- Comparison and Constants ---

    @Test
    void testCompare() {
        assertEquals(0, ObjectUtils.compare(null, null));
        assertEquals(-1, ObjectUtils.compare(null, 10, false));
        assertEquals(1, ObjectUtils.compare(null, 10, true));
        assertEquals(0, ObjectUtils.compare(10, 10));
        assertTrue(ObjectUtils.compare(20, 10) > 0);
    }

    @Test
    void testConst() {
        assertEquals((byte) 5, ObjectUtils.CONST_BYTE(5));
        assertThrows(IllegalArgumentException.class, () -> ObjectUtils.CONST_BYTE(128));
        assertEquals((short) 5, ObjectUtils.CONST_SHORT(5));
        assertThrows(IllegalArgumentException.class, () -> ObjectUtils.CONST_SHORT(40000));
        assertEquals("a", ObjectUtils.CONST("a"));
    }

    @Test
    void testNotEqual() {
        assertFalse(ObjectUtils.notEqual(null, null));
        assertTrue(ObjectUtils.notEqual(null, "a"));
        assertFalse(ObjectUtils.notEqual("a", "a"));
    }

    // --- Hashing and Identity ---

    @Test
    void testHashing() {
        assertEquals("0", ObjectUtils.hashCodeHex(null));
        assertNotNull(ObjectUtils.hashCodeHex("test"));
        assertEquals(1, ObjectUtils.hashCodeMulti((Object[]) null));
        assertTrue(ObjectUtils.hashCodeMulti("a", "b") != 0);
    }

    @Test
    void testIdentityToString() throws IOException {
        assertNull(ObjectUtils.identityToString(null));
        String result = ObjectUtils.identityToString("test");
        assertTrue(result.startsWith("java.lang.String@"));

        StringBuilder sb = new StringBuilder();
        ObjectUtils.identityToString(sb, "test");
        assertEquals(result, sb.toString());
        
        assertThrows(NullPointerException.class, () -> ObjectUtils.identityToString((StringBuilder)null, null));
    }

    // --- Emptiness and Requirements ---

    @Test
    void testIsEmpty() {
        assertTrue(ObjectUtils.isEmpty(null));
        assertTrue(ObjectUtils.isEmpty(""));
        assertTrue(ObjectUtils.isEmpty(new int[0]));
        assertTrue(ObjectUtils.isEmpty(Collections.emptyList()));
        assertTrue(ObjectUtils.isEmpty(Optional.empty()));
        assertFalse(ObjectUtils.isEmpty(" "));
        assertFalse(ObjectUtils.isEmpty(123));
    }

    @Test
    void testRequireNonEmpty() {
        assertEquals("a", ObjectUtils.requireNonEmpty("a"));
        assertThrows(NullPointerException.class, () -> ObjectUtils.requireNonEmpty(null));
        assertThrows(IllegalArgumentException.class, () -> ObjectUtils.requireNonEmpty(""));
    }

    // --- Min / Max / Median / Mode ---

    @Test
    void testMinMax() {
        assertEquals(Integer.valueOf(3), ObjectUtils.max(1, 2, 3));
        assertEquals(Integer.valueOf(1), ObjectUtils.min(1, 2, 3));
        assertEquals(Integer.valueOf(2), ObjectUtils.max(null, 2, 1));
        assertNull(ObjectUtils.max((Comparable[]) null));
    }

    @Test
    void testMedian() {
        assertEquals(2, ObjectUtils.median(1, 2, 3));
        assertEquals(2, ObjectUtils.median(1, 2, 3, 4)); // Lower middle
        assertThrows(IllegalArgumentException.class, () -> ObjectUtils.median(1, null));
        assertThrows(NullPointerException.class, () -> ObjectUtils.median((Comparator<Integer>) null, 1));
    }

    @Test
    void testMode() {
        assertNull(ObjectUtils.mode());
        assertEquals("a", ObjectUtils.mode("a", "b", "a"));
        assertNull(ObjectUtils.mode("a", "b")); // Tie
    }

    // --- String and Wait ---

    @Test
    void testToString() {
        assertEquals("", ObjectUtils.toString(null));
        assertEquals("test", ObjectUtils.toString("test"));
        assertEquals("null", ObjectUtils.toString(null, "null"));
        
        Supplier<String> expensive = () -> "expensive";
        assertEquals("expensive", ObjectUtils.toString((Object)null, expensive));
        assertEquals("test", ObjectUtils.toString("test", expensive));
    }

    @Test
    void testWait() {
        Object lock = new Object();
        assertThrows(IllegalMonitorStateException.class, () -> ObjectUtils.wait(lock, Duration.ofMillis(1)));
        assertThrows(IllegalArgumentException.class, () -> ObjectUtils.wait(lock, Duration.ofMillis(-1)));
    }
}
