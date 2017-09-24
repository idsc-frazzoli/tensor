// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;
import java.util.Random;

import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class TensorsTest extends TestCase {
  public void testFromString() {
    assertEquals(Tensors.fromString("{   }"), Tensors.empty());
    assertEquals(Tensors.fromString("{ 2 ,-3   , 4}"), Tensors.vector(2, -3, 4));
    assertEquals(Tensors.fromString("{   {2, -3, 4  }, {2.3,-.2   }, {  }   }"), //
        Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(2.3, -.2), Tensors.empty()));
  }

  public void testEmpty() {
    assertEquals(Tensors.empty(), Tensors.empty());
    assertEquals(Tensors.empty(), Tensors.vector());
    assertEquals(Tensors.empty(), Tensors.of());
  }

  public void testEquals() {
    Tensor a = Tensors.empty();
    assertFalse(a.equals(null));
    assertFalse(a.equals(Integer.valueOf(123)));
    assertFalse(a.equals(RealScalar.of(2)));
  }

  public void testReduction() {
    Tensor a = Tensors.vectorDouble(2., 1.123, .3123);
    boolean value = a.flatten(-1).map(Scalar.class::cast) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
        .map(d -> d > 0) //
        .reduce(Boolean::logicalAnd) //
        .orElse(true);
    assertTrue(value);
  }

  public void testGet() {
    assertTrue(IdentityMatrix.of(10).Get(3, 4) instanceof RealScalar);
  }

  public void testSet() {
    Tensor eye = IdentityMatrix.of(5);
    Tensor cpy = eye.copy();
    assertEquals(eye, cpy);
    cpy.set(DoubleScalar.of(.3), 1, 2);
    assertFalse(eye.equals(cpy));
    cpy.set(s -> (Scalar) s.negate(), 2, 2);
  }

  public void testDiagonalMatrix() {
    Tensor v = Tensors.vectorDouble(12, 3.2, .32);
    DiagonalMatrix.of(v);
  }

  public void testNorm() {
    Tensor a = Tensors.vectorLong(2, 3, 4, 5);
    Scalar s = (Scalar) a.dot(a);
    assertEquals(s, RationalScalar.of(4 + 9 + 16 + 25, 1));
  }

  public void testNorm2() {
    Tensor a = Tensors.of( //
        RationalScalar.of(2, 3), //
        RationalScalar.of(4, 5));
    Scalar s = (Scalar) a.dot(a);
    assertEquals(s, RationalScalar.of(244, 225));
  }

  public void testNorm3() {
    Tensor a = Tensors.vectorLong(2, -3, 4, -1);
    double ods = a.flatten(0) //
        .map(s -> (Scalar) s) //
        .map(Scalar::abs) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
        .reduce(Double::max) //
        .orElse(0.);
    assertEquals(ods, 4.0);
  }

  public void testNorm4() {
    Tensor a = Tensors.vectorLong(2, -3, 4, -1);
    double ods = a.flatten(0) //
        .map(s -> (Scalar) s) //
        .map(Scalar::abs) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
        .reduce(Double::sum) //
        .orElse(0.);
    assertEquals(ods, 10.0);
  }

  public void testNorm5() {
    int n = 6;
    int m = 12;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> //
    RationalScalar.of( //
        random.nextInt(100) - 50, //
        random.nextInt(100) + 1), n, m);
    Tensor c = Tensors.vector(i -> RationalScalar.of(1, 1), n);
    assertEquals(Total.of(A), c.dot(A));
  }

  public void testInteger() {
    Tensor p = Tensors.vector(Arrays.asList(3, 4, -5, 6));
    Tensor q = Tensors.vector(3, 4, -5, 6);
    Tensor r = Tensors.vector(3.0, 4.0, -5.0, 6.0);
    assertTrue(p.Get(0) instanceof RationalScalar);
    assertTrue(p.Get(1) instanceof RationalScalar);
    assertTrue(p.Get(2) instanceof RationalScalar);
    assertTrue(p.Get(3) instanceof RationalScalar);
    assertTrue(q.Get(0) instanceof RationalScalar);
    assertTrue(q.Get(1) instanceof RationalScalar);
    assertTrue(q.Get(2) instanceof RationalScalar);
    assertTrue(q.Get(3) instanceof RationalScalar);
    assertEquals(p, q);
    assertEquals(p, r);
  }

  public void testDoubleArray() {
    double[] asd = new double[] { 3.2, -0.3, 1.0 };
    Tensors.vectorDouble(asd);
  }

  public void testDouble() {
    Tensor p = Tensors.vector(Arrays.asList(3.3, 4., 5.3, 6.));
    Tensor q = Tensors.vector(3.3, 4., 5.3, 6.);
    assertEquals(p, q);
  }

  public void testNumber() {
    Tensor p = Tensors.vector(Arrays.asList(3, 4, 5.3, 6));
    Tensor q = Tensors.vector(3, 4, 5.3, 6);
    assertTrue(p.Get(0) instanceof RationalScalar);
    assertEquals(p, q);
  }

  public void testIntArrays() {
    int[][] data = new int[][] { { 1, -2, 3 }, { 4, 9 } };
    Tensor actual = Tensors.matrixInt(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9}}");
    assertEquals(expected, actual);
  }

  public void testLongArrays() {
    long[][] data = new long[][] { { 1, -2, 3 }, { 4, 9 }, { 0, 0, 0, 0, 0 }, {} };
    Tensor actual = Tensors.matrixLong(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9},{0,0,0,0,0},{}}");
    assertEquals(expected, actual);
  }

  public void testDoubleArrays() {
    double[][] data = new double[][] { { 1, -2, 3 }, { 4, 9 }, { 0, 0, 0, 0, 0 }, {} };
    Tensor actual = Tensors.matrixDouble(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9},{0,0,0,0,0},{}}");
    assertEquals(expected, actual);
  }

  public void testNumberArrays() {
    Number[][] data = new Number[][] { { 1, -2, 3 }, { 4, 9 }, { 0, 0, 0, 0, 0 }, {} };
    Tensor actual = Tensors.matrix(data);
    Tensor expected = Tensors.fromString("{{1, -2, 3}, {4, 9},{0,0,0,0,0},{}}");
    assertEquals(expected, actual);
  }

  public void testScalarArrays() {
    Scalar[][] data = new Scalar[][] { { RealScalar.ZERO, RealScalar.ONE }, {}, { ComplexScalar.of(2, 3) } };
    Tensor actual = Tensors.matrix(data);
    Tensor expected = Tensors.fromString("{{0, 1}, {}, {2+3*I}}");
    assertEquals(expected, actual);
  }

  public void testIsEmpty() {
    assertFalse(Tensors.isEmpty(RealScalar.ONE));
    assertTrue(Tensors.isEmpty(Tensors.empty()));
    assertTrue(Tensors.isEmpty(Tensors.vector()));
    assertFalse(Tensors.isEmpty(Tensors.vector(1, 2, 3)));
  }

  public void testIsUnmodifiable() {
    Tensor canwrite = Tensors.vector(1, 2, 3);
    Tensor readonly = canwrite.unmodifiable();
    assertFalse(Tensors.isUnmodifiable(canwrite));
    assertTrue(Tensors.isUnmodifiable(readonly));
    assertFalse(Tensors.isUnmodifiable(readonly.copy()));
  }
}