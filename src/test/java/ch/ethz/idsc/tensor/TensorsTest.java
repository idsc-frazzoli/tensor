// code by jph
package ch.ethz.idsc.tensor;

import java.util.Iterator;
import java.util.Random;

import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class TensorsTest extends TestCase {
  public void testFromString() {
    assertEquals(Tensors.fromString("[   ]"), Tensors.empty());
    assertEquals(Tensors.fromString("[ 2 ,-3   , 4]"), Tensors.vectorInt(2, -3, 4));
    assertEquals(Tensors.fromString("[   [2, -3, 4  ], [2.3,-.2   ], [  ]   ]"), //
        Tensors.of(Tensors.vectorInt(2, -3, 4), Tensors.vectorDouble(2.3, -.2), Tensors.empty()));
  }

  public void testUnmodifiableSet() {
    Tensor id = IdentityMatrix.of(3).unmodifiable();
    try {
      id.set(ZeroScalar.get(), 2, 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnmodifiableIterator() {
    Tensor id = IdentityMatrix.of(3).unmodifiable();
    Iterator<Tensor> iterator = id.iterator();
    iterator.next();
    try {
      iterator.remove();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
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
    {
      Tensor a = Tensors.vectorLong(2, 3, 4, 5);
      Scalar s = (Scalar) a.dot(a);
      assertEquals(s, RationalScalar.of(4 + 9 + 16 + 25, 1));
    }
    {
      Tensor a = Tensors.of( //
          RationalScalar.of(2, 3), //
          RationalScalar.of(4, 5));
      Scalar s = (Scalar) a.dot(a);
      assertEquals(s, RationalScalar.of(244, 225));
    }
    {
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
    {
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
    {
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
  }
}
