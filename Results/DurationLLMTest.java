package org.joda.time;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Comprehensive tests for Duration to achieve 100% line coverage 
 * and high mutation scores.
 */
public class DurationLLMTest {

    // -----------------------------------------------------------------------
    @Test
    public void testConstants() {
        assertEquals(0L, Duration.ZERO.getMillis());
    }

    @Test
    public void testParse() {
        assertEquals(new Duration(1000), Duration.parse("PT1S"));
        assertEquals(new Duration(60000), Duration.parse("PT60S"));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testFactoryMethods_StandardUnits() {
        // Days
        assertEquals(0, Duration.standardDays(0).getMillis());
        assertEquals(Duration.ZERO, Duration.standardDays(0));
        assertEquals(24 * 60 * 60 * 1000L, Duration.standardDays(1).getMillis());
        
        // Hours
        assertEquals(0, Duration.standardHours(0).getMillis());
        assertEquals(60 * 60 * 1000L, Duration.standardHours(1).getMillis());
        
        // Minutes
        assertEquals(0, Duration.standardMinutes(0).getMillis());
        assertEquals(60 * 1000L, Duration.standardMinutes(1).getMillis());
        
        // Seconds
        assertEquals(0, Duration.standardSeconds(0).getMillis());
        assertEquals(1000L, Duration.standardSeconds(1).getMillis());
        
        // Millis
        assertEquals(0, Duration.millis(0).getMillis());
        assertEquals(500L, Duration.millis(500).getMillis());
    }

    @Test(expected = ArithmeticException.class)
    public void testStandardDaysOverflow() {
        Duration.standardDays(Long.MAX_VALUE);
    }

    // -----------------------------------------------------------------------
    @Test
    public void testConstructors() {
        // long constructor
        Duration test = new Duration(1234L);
        assertEquals(1234L, test.getMillis());

        // start/end long constructor
        test = new Duration(1000L, 2500L);
        assertEquals(1500L, test.getMillis());

        // start/end ReadableInstant
        DateTime start = new DateTime(2010, 6, 1, 0, 0, 0, 0);
        DateTime end = new DateTime(2010, 6, 2, 0, 0, 0, 0);
        test = new Duration(start, end);
        assertEquals(24 * 60 * 60 * 1000L, test.getMillis());

        // Object constructor (via converter)
        test = new Duration("PT1.234S");
        assertEquals(1234L, test.getMillis());
    }

    // -----------------------------------------------------------------------
    @Test
    public void testGetStandardUnits_Rounding() {
        Duration test = new Duration(DateTimeConstants.MILLIS_PER_DAY + 1);
        assertEquals(1, test.getStandardDays());
        
        test = new Duration(DateTimeConstants.MILLIS_PER_HOUR + 1);
        assertEquals(1, test.getStandardHours());
        
        test = new Duration(DateTimeConstants.MILLIS_PER_MINUTE + 1);
        assertEquals(1, test.getStandardMinutes());
        
        test = new Duration(1999L);
        assertEquals(1, test.getStandardSeconds());
    }

    @Test
    public void testToStandardClasses() {
        Duration test = Duration.standardDays(2);
        assertEquals(Days.days(2), test.toStandardDays());
        assertEquals(Hours.hours(48), test.toStandardHours());
        assertEquals(Minutes.minutes(48 * 60), test.toStandardMinutes());
        assertEquals(Seconds.seconds(48 * 3600), test.toStandardSeconds());
        
        // Identity check
        assertSame(test, test.toDuration());
    }

    // -----------------------------------------------------------------------
    @Test
    public void testWithMillis() {
        Duration test = new Duration(1000L);
        assertSame(test, test.withMillis(1000L)); // Mutation test: check identity
        assertNotSame(test, test.withMillis(2000L));
        assertEquals(2000L, test.withMillis(2000L).getMillis());
    }

    @Test
    public void testPlusMethods() {
        Duration test = new Duration(1000L);
        
        // plus long
        assertEquals(1500L, test.plus(500L).getMillis());
        assertSame(test, test.plus(0L));
        
        // plus ReadableDuration
        assertEquals(1500L, test.plus(new Duration(500L)).getMillis());
        assertSame(test, test.plus((ReadableDuration) null));
    }

    @Test
    public void testMinusMethods() {
        Duration test = new Duration(1000L);
        
        // minus long
        assertEquals(500L, test.minus(500L).getMillis());
        assertSame(test, test.minus(0L));
        
        // minus ReadableDuration
        assertEquals(500L, test.minus(new Duration(500L)).getMillis());
        assertSame(test, test.minus((ReadableDuration) null));
    }

    @Test
    public void testMultipliedBy() {
        Duration test = new Duration(1000L);
        assertEquals(3000L, test.multipliedBy(3).getMillis());
        assertSame(test, test.multipliedBy(1));
        assertEquals(0, test.multipliedBy(0).getMillis());
    }

    @Test
    public void testDividedBy() {
        Duration test = new Duration(1000L);
        assertEquals(500L, test.dividedBy(2).getMillis());
        assertSame(test, test.dividedBy(1));
    }

    @Test(expected = ArithmeticException.class)
    public void testDividedByZero() {
        Duration.standardSeconds(1).dividedBy(0);
    }

    @Test
    public void testNegated() {
        Duration test = new Duration(1000L);
        assertEquals(-1000L, test.negated().getMillis());
        assertEquals(1000L, new Duration(-1000L).negated().getMillis());
        assertEquals(0L, Duration.ZERO.negated().getMillis());
    }

    @Test(expected = ArithmeticException.class)
    public void testNegatedOverflow() {
        new Duration(Long.MIN_VALUE).negated();
    }

    // -----------------------------------------------------------------------
    @Test
    public void testWithDurationAdded_Scalar() {
        Duration test = new Duration(1000L);
        
        // Test scalar 0 or duration 0 returns this (Mutation coverage)
        assertSame(test, test.withDurationAdded(0L, 1));
        assertSame(test, test.withDurationAdded(100L, 0));
        
        // Test subtraction via scalar
        assertEquals(500L, test.withDurationAdded(500L, -1).getMillis());
        
        // Test null ReadableDuration
        assertSame(test, test.withDurationAdded(null, 1));
    }

    @Test(expected = ArithmeticException.class)
    public void testAddOverflow() {
        new Duration(Long.MAX_VALUE).plus(1L);
    }
}
