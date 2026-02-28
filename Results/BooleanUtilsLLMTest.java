package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit tests for BooleanUtils focusing on line coverage and mutation testing.
 */
public class BooleanUtilsLLMTest {

    @Test
    void testConstructor() {
        assertNotNull(new BooleanUtils());
    }

    @Test
    void testAnd_Primitive() {
        assertTrue(BooleanUtils.and(new boolean[] {true, true}));
        assertFalse(BooleanUtils.and(new boolean[] {true, false}));
        assertFalse(BooleanUtils.and(new boolean[] {false, false}));
        assertTrue(BooleanUtils.and(new boolean[] {true}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.and((boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.and(new boolean[]{}));
    }

    @Test
    void testAnd_Object() {
        assertEquals(Boolean.TRUE, BooleanUtils.and(new Boolean[] {Boolean.TRUE, Boolean.TRUE}));
        assertEquals(Boolean.FALSE, BooleanUtils.and(new Boolean[] {Boolean.TRUE, Boolean.FALSE}));
        assertEquals(Boolean.FALSE, BooleanUtils.and(new Boolean[] {Boolean.TRUE, null}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.and((Boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.and(new Boolean[]{}));
    }

    @Test
    void testOr_Primitive() {
        assertTrue(BooleanUtils.or(new boolean[] {true, false}));
        assertTrue(BooleanUtils.or(new boolean[] {true, true}));
        assertFalse(BooleanUtils.or(new boolean[] {false, false}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.or((boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.or(new boolean[]{}));
    }

    @Test
    void testOr_Object() {
        assertEquals(Boolean.TRUE, BooleanUtils.or(new Boolean[] {Boolean.FALSE, Boolean.TRUE}));
        assertEquals(Boolean.FALSE, BooleanUtils.or(new Boolean[] {Boolean.FALSE, Boolean.FALSE}));
        assertEquals(Boolean.TRUE, BooleanUtils.or(new Boolean[] {Boolean.FALSE, Boolean.TRUE, null}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.or((Boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.or(new Boolean[]{}));
    }

    @Test
    void testXor_Primitive() {
        assertTrue(BooleanUtils.xor(new boolean[] {true, false}));
        assertFalse(BooleanUtils.xor(new boolean[] {true, true}));
        assertFalse(BooleanUtils.xor(new boolean[] {false, false}));
        assertTrue(BooleanUtils.xor(new boolean[] {true, true, true}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.xor((boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.xor(new boolean[]{}));
    }

    @Test
    void testXor_Object() {
        assertEquals(Boolean.FALSE, BooleanUtils.xor(new Boolean[] {Boolean.TRUE, Boolean.TRUE}));
        assertEquals(Boolean.TRUE, BooleanUtils.xor(new Boolean[] {Boolean.TRUE, Boolean.FALSE}));
        assertEquals(Boolean.TRUE, BooleanUtils.xor(new Boolean[] {Boolean.TRUE, null}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.xor((Boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.xor(new Boolean[]{}));
    }

    @Test
    void testOneHot_Primitive() {
        assertTrue(BooleanUtils.oneHot(new boolean[] {true, false, false}));
        assertFalse(BooleanUtils.oneHot(new boolean[] {true, true, false}));
        assertFalse(BooleanUtils.oneHot(new boolean[] {false, false, false}));
        assertThrows(NullPointerException.class, () -> BooleanUtils.oneHot((boolean[]) null));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.oneHot(new boolean[]{}));
    }

    @Test
    void testOneHot_Object() {
        assertEquals(Boolean.TRUE, BooleanUtils.oneHot(new Boolean[] {Boolean.TRUE, Boolean.FALSE}));
        assertEquals(Boolean.FALSE, BooleanUtils.oneHot(new Boolean[] {Boolean.TRUE, Boolean.TRUE}));
        assertEquals(Boolean.TRUE, BooleanUtils.oneHot(new Boolean[] {null, Boolean.TRUE}));
    }

    @Test
    void testIsMethods() {
        assertTrue(BooleanUtils.isTrue(Boolean.TRUE));
        assertFalse(BooleanUtils.isTrue(Boolean.FALSE));
        assertFalse(BooleanUtils.isTrue(null));

        assertTrue(BooleanUtils.isFalse(Boolean.FALSE));
        assertFalse(BooleanUtils.isFalse(Boolean.TRUE));
        assertFalse(BooleanUtils.isFalse(null));

        assertTrue(BooleanUtils.isNotTrue(Boolean.FALSE));
        assertTrue(BooleanUtils.isNotTrue(null));
        assertFalse(BooleanUtils.isNotTrue(Boolean.TRUE));

        assertTrue(BooleanUtils.isNotFalse(Boolean.TRUE));
        assertTrue(BooleanUtils.isNotFalse(null));
        assertFalse(BooleanUtils.isNotFalse(Boolean.FALSE));
    }

    @Test
    void testNegate() {
        assertEquals(Boolean.FALSE, BooleanUtils.negate(Boolean.TRUE));
        assertEquals(Boolean.TRUE, BooleanUtils.negate(Boolean.FALSE));
        assertNull(BooleanUtils.negate(null));
    }

    @Test
    void testToBoolean_Primitive() {
        assertTrue(BooleanUtils.toBoolean(1));
        assertTrue(BooleanUtils.toBoolean(-1));
        assertFalse(BooleanUtils.toBoolean(0));
    }

    @Test
    void testToBoolean_Object() {
        assertTrue(BooleanUtils.toBoolean(Boolean.TRUE));
        assertFalse(BooleanUtils.toBoolean(Boolean.FALSE));
        assertFalse(BooleanUtils.toBoolean((Boolean) null));
    }

    @Test
    void testToBoolean_IntValues() {
        assertTrue(BooleanUtils.toBoolean(1, 1, 2));
        assertFalse(BooleanUtils.toBoolean(2, 1, 2));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.toBoolean(3, 1, 2));
    }

    @Test
    void testToBoolean_IntegerObjects() {
        assertTrue(BooleanUtils.toBoolean(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(0)));
        assertFalse(BooleanUtils.toBoolean(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0)));
        assertTrue(BooleanUtils.toBoolean((Integer) null, null, Integer.valueOf(0)));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.toBoolean(Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(0)));
    }

    @Test
    void testToBoolean_String() {
        assertTrue(BooleanUtils.toBoolean("true"));
        assertTrue(BooleanUtils.toBoolean("Y"));
        assertTrue(BooleanUtils.toBoolean("on"));
        assertTrue(BooleanUtils.toBoolean("yes"));
        assertFalse(BooleanUtils.toBoolean("false"));
        assertFalse(BooleanUtils.toBoolean((String) null));
    }

    @Test
    void testToBooleanObject_String() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("t"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("1"));
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("0"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("on"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject("true"));
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject("false"));
        assertNull(BooleanUtils.toBooleanObject("blue"));
        assertNull(BooleanUtils.toBooleanObject((String) null));
    }

    @Test
    void testToBooleanObject_Int() {
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(1));
        assertEquals(Boolean.FALSE, BooleanUtils.toBooleanObject(0));
        assertEquals(Boolean.TRUE, BooleanUtils.toBooleanObject(5, 5, 0, 1));
        assertNull(BooleanUtils.toBooleanObject(1, 5, 0, 1));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.toBooleanObject(9, 1, 2, 3));
    }

    @Test
    void testToInteger() {
        assertEquals(1, BooleanUtils.toInteger(true));
        assertEquals(0, BooleanUtils.toInteger(false));
        assertEquals(10, BooleanUtils.toInteger(true, 10, 20));
        assertEquals(30, BooleanUtils.toInteger((Boolean) null, 10, 20, 30));
    }

    @Test
    void testToIntegerObject() {
        assertEquals(Integer.valueOf(1), BooleanUtils.toIntegerObject(true));
        assertEquals(Integer.valueOf(0), BooleanUtils.toIntegerObject(false));
        assertNull(BooleanUtils.toIntegerObject((Boolean) null));
        assertEquals(Integer.valueOf(5), BooleanUtils.toIntegerObject(Boolean.TRUE, Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(-1)));
    }

    @Test
    void testToStringStandard() {
        assertEquals("on", BooleanUtils.toStringOnOff(true));
        assertEquals("off", BooleanUtils.toStringOnOff(false));
        assertNull(BooleanUtils.toStringOnOff((Boolean) null));
        assertEquals("true", BooleanUtils.toStringTrueFalse(true));
        assertEquals("no", BooleanUtils.toStringYesNo(false));
    }

    @Test
    void testToStringCustom() {
        assertEquals("Y", BooleanUtils.toString(true, "Y", "N"));
        assertEquals("?", BooleanUtils.toString((Boolean) null, "Y", "N", "?"));
    }

    @Test
    void testCompare() {
        assertEquals(0, BooleanUtils.compare(true, true));
        assertTrue(BooleanUtils.compare(true, false) > 0);
        assertTrue(BooleanUtils.compare(false, true) < 0);
    }

    @Test
    void testValues() {
        assertArrayEquals(new boolean[]{false, true}, BooleanUtils.primitiveValues());
        assertArrayEquals(new Boolean[]{Boolean.FALSE, Boolean.TRUE}, BooleanUtils.booleanValues());
        assertEquals(2, BooleanUtils.values().size());
    }

    @Test
    void testForEach() {
        AtomicInteger count = new AtomicInteger(0);
        BooleanUtils.forEach(b -> count.incrementAndGet());
        assertEquals(2, count.get());
    }
}
