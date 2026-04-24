package org.apache.commons.math3.linear;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class ArrayRealVectorLLMTest {

    private static final double EPS = 1e-12;

    // --- Constructor Tests ---

    @Test
    public void testConstructors() {
        // Default
        Assert.assertEquals(0, new ArrayRealVector().getDimension());

        // Size only
        Assert.assertEquals(3, new ArrayRealVector(3).getDimension());

        // Size and preset
        ArrayRealVector vPreset = new ArrayRealVector(3, 1.5);
        Assert.assertArrayEquals(new double[]{1.5, 1.5, 1.5}, vPreset.toArray(), EPS);

        // double[] basic
        double[] data = {1, 2, 3};
        ArrayRealVector vData = new ArrayRealVector(data);
        Assert.assertNotSame(data, vData.getDataRef()); // Should be clone

        // double[] with shallow copy option
        Assert.assertSame(data, new ArrayRealVector(data, false).getDataRef());
        
        // double[] subset
        ArrayRealVector vSub = new ArrayRealVector(new double[]{0, 1, 2, 3, 4}, 1, 3);
        Assert.assertArrayEquals(new double[]{1, 2, 3}, vSub.toArray(), EPS);

        // Double[] array
        Double[] dObj = {1.0, 2.0};
        Assert.assertArrayEquals(new double[]{1, 2}, new ArrayRealVector(dObj).toArray(), EPS);

        // Double[] subset
        Assert.assertArrayEquals(new double[]{2, 3}, new ArrayRealVector(new Double[]{1.0, 2.0, 3.0}, 1, 2).toArray(), EPS);
    }

    @Test(expected = NullArgumentException.class)
    public void testConstructorNull() {
        new ArrayRealVector((double[]) null);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testConstructorOutofBounds() {
        new ArrayRealVector(new double[]{1, 2}, 1, 5);
    }

    // --- Mathematical Operations (Optimized vs Generic) ---

    @Test
    public void testAdd() {
        ArrayRealVector v1 = new ArrayRealVector(new double[]{1, 2});
        ArrayRealVector v2 = new ArrayRealVector(new double[]{3, 4});
        
        // Optimized path
        Assert.assertArrayEquals(new double[]{4, 6}, v1.add(v2).toArray(), EPS);
        
        // Generic path
        RealVector vGeneric = new RealVectorTestImpl(new double[]{3, 4});
        Assert.assertArrayEquals(new double[]{4, 6}, v1.add(vGeneric).toArray(), EPS);
    }

    @Test(expected = DimensionMismatchException.class)
    public void testAddDimensionMismatch() {
        new ArrayRealVector(2).add(new ArrayRealVector(3));
    }

    @Test
    public void testMapFunctions() {
        ArrayRealVector v = new ArrayRealVector(new double[]{1, 2});
        UnivariateFunction square = x -> x * x;
        
        Assert.assertArrayEquals(new double[]{1, 4}, v.map(square).toArray(), EPS);
        v.mapToSelf(square);
        Assert.assertArrayEquals(new double[]{1, 4}, v.toArray(), EPS);
        
        v.mapAddToSelf(2).mapSubtractToSelf(1).mapMultiplyToSelf(2).mapDivideToSelf(4);
        // ((4+2)-1)*2 / 4 = 2.5
        Assert.assertEquals(2.5, v.getEntry(1), EPS);
    }

    @Test
    public void testEbeOperations() {
        ArrayRealVector v1 = new ArrayRealVector(new double[]{1, 10});
        ArrayRealVector v2 = new ArrayRealVector(new double[]{2, 2});
        
        Assert.assertArrayEquals(new double[]{2, 20}, v1.ebeMultiply(v2).toArray(), EPS);
        Assert.assertArrayEquals(new double[]{0.5, 5}, v1.ebeDivide(v2).toArray(), EPS);
        
        // Generic path
        RealVector vG = new RealVectorTestImpl(new double[]{2, 2});
        Assert.assertArrayEquals(new double[]{2, 20}, v1.ebeMultiply(vG).toArray(), EPS);
    }

    // --- Norms and Distances ---

    @Test
    public void testNorms() {
        ArrayRealVector v = new ArrayRealVector(new double[]{-3, 4});
        Assert.assertEquals(5.0, v.getNorm(), EPS);
        Assert.assertEquals(7.0, v.getL1Norm(), EPS);
        Assert.assertEquals(4.0, v.getLInfNorm(), EPS);
    }

    @Test
    public void testDistances() {
        ArrayRealVector v1 = new ArrayRealVector(new double[]{1, 2});
        ArrayRealVector v2 = new ArrayRealVector(new double[]{4, 6});
        
        Assert.assertEquals(5.0, v1.getDistance(v2), EPS);
        Assert.assertEquals(7.0, v1.getL1Distance(v2), EPS);
        Assert.assertEquals(4.0, v1.getLInfDistance(v2), EPS);
        
        // Generic path for coverage
        RealVector vG = new RealVectorTestImpl(new double[]{4, 6});
        Assert.assertEquals(5.0, v1.getDistance(vG), EPS);
        Assert.assertEquals(7.0, v1.getL1Distance(vG), EPS);
        Assert.assertEquals(4.0, v1.getLInfDistance(vG), EPS);
    }

    // --- Boundary and Exception Cases ---

    @Test(expected = OutOfRangeException.class)
    public void testGetEntryInvalid() {
        new ArrayRealVector(2).getEntry(5);
    }

    @Test
    public void testSetSubVector() {
        ArrayRealVector v = new ArrayRealVector(new double[]{0, 0, 0, 0});
        v.setSubVector(1, new double[]{1, 2});
        Assert.assertArrayEquals(new double[]{0, 1, 2, 0}, v.toArray(), EPS);
        
        // Trigger IndexOutOfBounds catch block
        try {
            v.setSubVector(3, new double[]{1, 2});
            Assert.fail("Should throw OutOfRangeException");
        } catch (OutOfRangeException e) {
            // Success
        }
    }

    @Test
    public void testIsNaNInfinite() {
        ArrayRealVector vNaN = new ArrayRealVector(new double[]{1, Double.NaN});
        ArrayRealVector vInf = new ArrayRealVector(new double[]{1, Double.POSITIVE_INFINITY});
        
        Assert.assertTrue(vNaN.isNaN());
        Assert.assertFalse(vNaN.isInfinite());
        Assert.assertTrue(vInf.isInfinite());
        Assert.assertFalse(vInf.isNaN());
    }

    @Test
    public void testCombine() {
        ArrayRealVector v1 = new ArrayRealVector(new double[]{1, 2});
        ArrayRealVector v2 = new ArrayRealVector(new double[]{3, 4});
        v1.combineToSelf(2, 3, v2); // 2*1 + 3*3 = 11, 2*2 + 3*4 = 16
        Assert.assertArrayEquals(new double[]{11, 16}, v1.toArray(), EPS);
    }

    @Test
    public void testWalkers() {
        ArrayRealVector v = new ArrayRealVector(new double[]{1, 2, 3});
        
        double sum = v.walkInDefaultOrder(new RealVectorPreservingVisitor() {
            double s = 0;
            public void start(int d, int start, int end) {}
            public void visit(int i, double value) { s += value; }
            public double end() { return s; }
        });
        Assert.assertEquals(6.0, sum, EPS);
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayRealVector v1 = new ArrayRealVector(new double[]{1, 2});
        ArrayRealVector v2 = new ArrayRealVector(new double[]{1, 2});
        ArrayRealVector v3 = new ArrayRealVector(new double[]{1, 3});
        ArrayRealVector vNaN = new ArrayRealVector(new double[]{Double.NaN});
        
        Assert.assertTrue(v1.equals(v1));
        Assert.assertTrue(v1.equals(v2));
        Assert.assertFalse(v1.equals(v3));
        Assert.assertFalse(v1.equals(null));
        Assert.assertFalse(v1.equals("string"));
        
        Assert.assertEquals(v1.hashCode(), v2.hashCode());
        Assert.assertEquals(9, vNaN.hashCode());
    }

    // --- Helper Mock for Generic RealVector Paths ---
    private static class RealVectorTestImpl extends RealVector {
        private final double[] data;
        RealVectorTestImpl(double[] d) { this.data = d; }
        @Override public int getDimension() { return data.length; }
        @Override public double getEntry(int i) { return data[i]; }
        @Override public void setEntry(int i, double v) { data[i] = v; }
        @Override public RealVector append(RealVector v) { return null; }
        @Override public RealVector append(double d) { return null; }
        @Override public RealVector getSubVector(int i, int n) { return null; }
        @Override public void setSubVector(int i, RealVector v) {}
        @Override public boolean isNaN() { return false; }
        @Override public boolean isInfinite() { return false; }
        @Override public RealVector copy() { return null; }
    }
}
