package org.apache.commons.math3.fraction;

import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigInteger;

public class FractionLLMTest {

    // --- Constructor Tests (Int, Int) ---

    @Test(expected = MathArithmeticException.class)
    public void testConstructorZeroDenominator() {
        new Fraction(1, 0);
    }

    @Test
    public void testConstructorReduction() {
        Fraction f = new Fraction(2, 4);
        assertEquals(1, f.getNumerator());
        assertEquals(2, f.getDenominator());

        f = new Fraction(-2, -4);
        assertEquals(1, f.getNumerator());
        assertEquals(2, f.getDenominator());
    }

    @Test(expected = MathArithmeticException.class)
    public void testConstructorOverflow() {
        new Fraction(Integer.MIN_VALUE, -1);
    }

    // --- Constructor Tests (Double & Continued Fractions) ---

    @Test
    public void testDoubleConstructor() {
        Fraction f = new Fraction(0.75);
        assertEquals(3, f.getNumerator());
        assertEquals(4, f.getDenominator());
    }

    @Test(expected = FractionConversionException.class)
    public void testDoubleConstructorOverflow() {
        new Fraction(1e15); // Exceeds Integer.MAX_VALUE
    }

    @Test
    public void testDoubleConstructorWithEpsilon() {
        // Test termination via epsilon
        Fraction f = new Fraction(0.666666666666, 0.0001, 100);
        assertEquals(2, f.getNumerator());
        assertEquals(3, f.getDenominator());
    }

    @Test(expected = FractionConversionException.class)
    public void testDoubleConstructorMaxIterationsExceeded() {
        new Fraction(0.123456789, 1e-15, 2);
    }

    @Test
    public void testDoubleConstructorMaxDenominator() {
        // Test termination via maxDenominator
        Fraction f = new Fraction(0.6152, 10); 
        assertTrue(f.getDenominator() <= 10);
    }

    // --- Arithmetic Tests ---

    @Test
    public void testAdd() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 3);
        Fraction result = f1.add(f2);
        assertEquals(5, result.getNumerator());
        assertEquals(6, result.getDenominator());

        assertEquals(f1, f1.add(Fraction.ZERO));
        assertEquals(f2, Fraction.ZERO.add(f2));
    }

    @Test(expected = NullArgumentException.class)
    public void testAddNull() {
        Fraction.ONE.add((Fraction) null);
    }

    @Test
    public void testAddInteger() {
        Fraction f = new Fraction(1, 2).add(1);
        assertEquals(3, f.getNumerator());
    }

    @Test(expected = MathArithmeticException.class)
    public void testAddOverflow() {
        new Fraction(Integer.MAX_VALUE, 1).add(new Fraction(1, 1));
    }

    @Test
    public void testSubtract() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 6);
        Fraction result = f1.subtract(f2);
        assertEquals(1, result.getNumerator());
        assertEquals(3, result.getDenominator());
    }

    @Test
    public void testMultiply() {
        Fraction f1 = new Fraction(2, 3);
        Fraction f2 = new Fraction(3, 4);
        Fraction result = f1.multiply(f2);
        assertEquals(1, result.getNumerator());
        assertEquals(2, result.getDenominator());

        assertEquals(Fraction.ZERO, f1.multiply(Fraction.ZERO));
    }

    @Test
    public void testDivide() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 4);
        Fraction result = f1.divide(f2);
        assertEquals(2, result.getNumerator());
        assertEquals(1, result.getDenominator());
    }

    @Test(expected = MathArithmeticException.class)
    public void testDivideByZero() {
        Fraction.ONE.divide(Fraction.ZERO);
    }

    // --- Edge Cases & Math Logic ---

    @Test
    public void testNegate() {
        Fraction f = new Fraction(1, 2).negate();
        assertEquals(-1, f.getNumerator());
    }

    @Test(expected = MathArithmeticException.class)
    public void testNegateOverflow() {
        new Fraction(Integer.MIN_VALUE, 1).negate();
    }

    @Test
    public void testAbs() {
        assertEquals(new Fraction(1, 2), new Fraction(-1, 2).abs());
        assertEquals(new Fraction(1, 2), new Fraction(1, 2).abs());
    }

    @Test
    public void testReciprocal() {
        Fraction f = new Fraction(2, 3).reciprocal();
        assertEquals(3, f.getNumerator());
        assertEquals(2, f.getDenominator());
    }

    // --- Utilities & Overrides ---

    @Test
    public void testCompareTo() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(1, 3);
        assertTrue(first.compareTo(second) > 0);
        assertTrue(second.compareTo(first) < 0);
        assertEquals(0, first.compareTo(new Fraction(2, 4)));
    }

    @Test
    public void testEqualsAndHashCode() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(1, 3);

        assertTrue(f1.equals(f1));
        assertTrue(f1.equals(f2));
        assertFalse(f1.equals(f3));
        assertFalse(f1.equals(null));
        assertFalse(f1.equals("Not a fraction"));

        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    public void testValues() {
        Fraction f = new Fraction(1, 2);
        assertEquals(0.5, f.doubleValue(), 0.0);
        assertEquals(0.5f, f.floatValue(), 0.0f);
        assertEquals(0, f.intValue());
        assertEquals(0L, f.longValue());
        assertEquals(50.0, f.percentageValue(), 0.0);
    }

    @Test
    public void testToString() {
        assertEquals("1 / 2", new Fraction(1, 2).toString());
        assertEquals("3", new Fraction(3, 1).toString());
        assertEquals("0", new Fraction(0, 5).toString());
    }

    @Test
    public void testGetReducedFraction() {
        Fraction f = Fraction.getReducedFraction(2, 4);
        assertEquals(1, f.getNumerator());
        assertEquals(2, f.getDenominator());

        assertEquals(Fraction.ZERO, Fraction.getReducedFraction(0, 5));
        
        // Test Integer.MIN_VALUE case in getReducedFraction
        Fraction fMin = Fraction.getReducedFraction(2, Integer.MIN_VALUE);
        assertEquals(-1, fMin.getNumerator());
        assertEquals(1073741824, fMin.getDenominator());
    }

    @Test
    public void testGetField() {
        assertNotNull(Fraction.ONE.getField());
    }
}
