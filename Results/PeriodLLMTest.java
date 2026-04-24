package org.joda.time;

import static org.junit.Assert.*;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.ISOPeriodFormat;
import org.junit.Test;
import java.io.Serializable;

public class PeriodLLMTest {

    // -----------------------------------------------------------------------
    @Test
    public void testConstantsAndStaticFactories() {
        assertEquals(0, Period.ZERO.getYears());
        assertEquals(PeriodType.standard(), Period.ZERO.getPeriodType());

        assertEquals(5, Period.years(5).getYears());
        assertEquals(4, Period.months(4).getMonths());
        assertEquals(3, Period.weeks(3).getWeeks());
        assertEquals(2, Period.days(2).getDays());
        assertEquals(1, Period.hours(1).getHours());
        assertEquals(10, Period.minutes(10).getMinutes());
        assertEquals(20, Period.seconds(20).getSeconds());
        assertEquals(500, Period.millis(500).getMillis());
    }

    @Test
    public void testParse() {
        Period expected = Period.years(1).withMonths(2);
        assertEquals(expected, Period.parse("P1Y2M"));
        assertEquals(expected, Period.parse("P1Y2M", ISOPeriodFormat.standard()));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testConstructors() {
        // Default
        assertEquals(0, new Period().getMillis());

        // H, M, S, ms
        Period pTime = new Period(1, 2, 3, 4);
        assertEquals(1, pTime.getHours());
        assertEquals(4, pTime.getMillis());

        // All fields
        Period pAll = new Period(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(1, pAll.getYears());
        assertEquals(8, pAll.getMillis());

        // Long duration
        long dur = 3600000L + 60000L + 1000L + 1L;
        Period pDur = new Period(dur);
        assertEquals(1, pDur.getHours());
        assertEquals(1, pDur.getMillis());

        // Start/End Long
        Period pInstants = new Period(0L, 3600000L);
        assertEquals(1, pInstants.getHours());
        
        // Copy constructor
        Period copy = new Period(pAll);
        assertEquals(pAll, copy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFieldDifference_Null() {
        Period.fieldDifference(null, null);
    }

    @Test
    public void testFieldDifference_Valid() {
        LocalDate start = new LocalDate(2005, 6, 9);
        LocalDate end = new LocalDate(2007, 4, 12);
        Period result = Period.fieldDifference(start, end);
        // P1Y-2M3D logic from javadoc
        assertEquals(1, result.getYears());
        assertEquals(-2, result.getMonths());
        assertEquals(3, result.getDays());
    }

    // -----------------------------------------------------------------------
    @Test
    public void testWithMethods() {
        Period base = new Period(1, 1, 1, 1, 1, 1, 1, 1);
        
        assertEquals(5, base.withYears(5).getYears());
        assertEquals(5, base.withMonths(5).getMonths());
        assertEquals(5, base.withWeeks(5).getWeeks());
        assertEquals(5, base.withDays(5).getDays());
        assertEquals(5, base.withHours(5).getHours());
        assertEquals(5, base.withMinutes(5).getMinutes());
        assertEquals(5, base.withSeconds(5).getSeconds());
        assertEquals(5, base.withMillis(5).getMillis());
        
        // withField
        assertEquals(10, base.withField(DurationFieldType.years(), 10).getYears());
        // withFieldAdded
        assertEquals(11, base.withFieldAdded(DurationFieldType.years(), 10).getYears());
        assertEquals(base, base.withFieldAdded(DurationFieldType.years(), 0));
    }

    @Test
    public void testPlusMinusMethods() {
        Period p = Period.hours(1);
        
        assertEquals(2, p.plusHours(1).getHours());
        assertEquals(0, p.plusHours(-1).getHours());
        assertEquals(p, p.plusHours(0));
        
        assertEquals(2, p.minusHours(-1).getHours());
        assertEquals(0, p.minusHours(1).getHours());

        // plus(ReadablePeriod)
        Period p2 = Period.minutes(30);
        Period combined = p.plus(p2);
        assertEquals(1, combined.getHours());
        assertEquals(30, combined.getMinutes());
        assertEquals(p, p.plus(null));
        
        // minus(ReadablePeriod)
        Period sub = combined.minus(p2);
        assertEquals(1, sub.getHours());
        assertEquals(0, sub.getMinutes());
        assertEquals(p, p.minus(null));
    }

    // -----------------------------------------------------------------------
    @Test
    public void testMathOperations() {
        Period p = new Period(1, 2, 3, 4, 5, 6, 7, 8);
        
        // multipliedBy
        Period doubleP = p.multipliedBy(2);
        assertEquals(2, doubleP.getYears());
        assertEquals(16, doubleP.getMillis());
        assertEquals(Period.ZERO, Period.ZERO.multipliedBy(5));
        assertEquals(p, p.multipliedBy(1));
        
        // negated
        Period neg = p.negated();
        assertEquals(-1, neg.getYears());
        assertEquals(-8, neg.getMillis());
    }

    @Test(expected = ArithmeticException.class)
    public void testMultipliedBy_Overflow() {
        Period.years(Integer.MAX_VALUE).multipliedBy(2);
    }

    // -----------------------------------------------------------------------
    @Test
    public void testStandardConversions() {
        Period p = Period.days(1).withHours(1);
        
        // 1 day (24h) + 1 hour = 25h
        assertEquals(25, p.toStandardHours().getHours());
        // 25 * 60 = 1500 min
        assertEquals(1500, p.toStandardMinutes().getMinutes());
        // 1500 * 60 = 90000 sec
        assertEquals(90000, p.toStandardSeconds().getSeconds());
        
        assertEquals(25 * 3600000L, p.toStandardDuration().getMillis());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testStandardConversion_FailOnMonths() {
        Period.months(1).toStandardDays();
    }

    // -----------------------------------------------------------------------
    @Test
    public void testNormalization() {
        // 1 year 15 months -> 2 years 3 months
        Period p = new Period(1, 15, 0, 0, 0, 0, 0, 0);
        Period norm = p.normalizedStandard();
        assertEquals(2, norm.getYears());
        assertEquals(3, norm.getMonths());

        // 70 minutes -> 1 hour 10 minutes
        Period pTime = Period.minutes(70);
        Period normTime = pTime.normalizedStandard();
        assertEquals(1, normTime.getHours());
        assertEquals(10, normTime.getMinutes());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNormalizedStandard_MissingFields() {
        // Period has months, but we request a type that doesn't support them
        Period p = Period.months(1);
        p.normalizedStandard(PeriodType.time());
    }

    @Test
    public void testToPeriodAndType() {
        Period p = Period.days(1);
        assertSame(p, p.toPeriod());
        
        Period result = p.withPeriodType(PeriodType.dayTime());
        assertEquals(PeriodType.dayTime(), result.getPeriodType());
        assertSame(p, p.withPeriodType(PeriodType.standard())); // No change
    }
}
