// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.GeometricDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class LinearInterpolationTest extends TestCase {
  public void testEmpty() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.empty());
    assertEquals(interpolation.get(Tensors.empty()), Tensors.empty());
  }

  public void testEmpty1() {
    Tensor tensor = Tensors.vector(10, 20, 30, 40);
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Tensor res = interpolation.get(Tensors.empty());
    assertEquals(res, tensor);
  }

  public void testEmpty2() {
    Tensor tensor = Tensors.vector(10, 20, 30, 40);
    Tensor ori = tensor.copy();
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Tensor res = interpolation.get(Tensors.empty());
    res.set(Increment.ONE, Tensor.ALL);
    assertEquals(tensor, ori);
    assertFalse(tensor.equals(res));
    assertEquals(interpolation.get(Tensors.empty()), ori);
  }

  public void testVectorGet() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.vector(10, 20, 30, 40));
    assertEquals(interpolation.get(Tensors.vector(0)), RealScalar.of(10));
    assertEquals(interpolation.get(Tensors.vector(2)), RealScalar.of(30));
    assertEquals(interpolation.get(Tensors.vector(2.5)), RealScalar.of(35));
    assertEquals(interpolation.get(Tensors.vector(3)), RealScalar.of(40));
  }

  public void testVectorAt() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.vector(10, 20, 30, 40));
    assertEquals(interpolation.At(RealScalar.of(0)), RealScalar.of(10));
    assertEquals(interpolation.At(RealScalar.of(2)), RealScalar.of(30));
    assertEquals(interpolation.At(RealScalar.of(2.5)), RealScalar.of(35));
    assertEquals(interpolation.At(RealScalar.of(3)), RealScalar.of(40));
  }

  public void testMatrix1() {
    Tensor tensor = Tensors.matrix(new Number[][] { { 5, 5, 5 }, { 1, 10, 100 } });
    Interpolation interpolation = LinearInterpolation.of(tensor);
    {
      Tensor res = interpolation.get(Tensors.vector(1, 3).multiply(RationalScalar.of(1, 2)));
      assertEquals(res, RealScalar.of(30)); // 5+5+10+100==120 -> 120 / 4 == 30
    }
    {
      Tensor res = interpolation.get(Tensors.of(RationalScalar.of(1, 2)));
      Tensor from = Tensors.fromString("{3, 15/2, 105/2}");
      assertEquals(res, from);
    }
  }

  public void testMatrix2() {
    Tensor tensor = Tensors.matrix(new Number[][] { { 5, 5, 5 }, { 1, 10, 100 } });
    Interpolation interpolation = LinearInterpolation.of(tensor);
    assertEquals(interpolation.get(UnitVector.of(1, 0)), Tensors.vector(1, 10, 100));
    assertEquals(interpolation.get(Tensors.vector(1, 2)), RealScalar.of(100));
    assertEquals(interpolation.get(Tensors.vector(0)), Tensors.vector(5, 5, 5));
    assertEquals(interpolation.get(UnitVector.of(2, 0)), RealScalar.of(1));
    assertEquals(interpolation.get(Array.zeros(2)), RealScalar.of(5));
  }

  public void testRank3() {
    Tensor arr = Array.of(Tensors::vector, 2, 3);
    Interpolation interpolation = LinearInterpolation.of(arr);
    Scalar result = interpolation.Get(Tensors.vector(0.3, 1.8, 0.3));
    assertFalse(ExactScalarQ.of(result));
    assertEquals(result, RationalScalar.of(3, 4));
  }

  public void testQuantity() {
    Tensor vector = Tensors.of(Quantity.of(1, "m"), Quantity.of(4, "m"), Quantity.of(2, "m"));
    Interpolation interpolation = LinearInterpolation.of(vector);
    Scalar r = Quantity.of((1 + 4) * 0.5, "m");
    Scalar s = interpolation.Get(Tensors.vector(0.5));
    assertEquals(s, r);
  }

  public void testQuantity2() {
    Tensor v1 = Tensors.of(Quantity.of(1, "m"), Quantity.of(4, "m"), Quantity.of(2, "m"));
    Tensor v2 = Tensors.of(Quantity.of(9, "s"), Quantity.of(6, "s"), Quantity.of(-3, "s"));
    Tensor matrix = Transpose.of(Tensors.of(v1, v2));
    Interpolation interpolation = LinearInterpolation.of(matrix);
    Scalar r1 = Quantity.of((1 + 4) * 0.5, "m");
    Scalar r2 = Quantity.of((9 + 6) * 0.5, "s");
    Tensor vec = interpolation.get(Tensors.of(RationalScalar.HALF));
    assertEquals(vec, Tensors.of(r1, r2));
    assertTrue(ExactScalarQ.all(vec));
    Tensor at = interpolation.at(RationalScalar.HALF);
    assertEquals(at, Tensors.of(r1, r2));
    assertTrue(ExactScalarQ.all(at));
  }

  public void testExact() {
    Distribution distribution = GeometricDistribution.of(RationalScalar.of(1, 3));
    Tensor matrix = RandomVariate.of(distribution, 3, 5);
    Interpolation interpolation = LinearInterpolation.of(matrix);
    Scalar index = RationalScalar.of(45, 31);
    Tensor res1 = interpolation.at(index);
    Tensor res2 = interpolation.get(Tensors.of(index));
    VectorQ.requireLength(res1, 5);
    assertTrue(ExactScalarQ.all(res1));
    assertTrue(ExactScalarQ.all(res2));
    assertEquals(res1, res2);
  }

  public void testUseCase() {
    Tensor tensor = Range.of(1, 11);
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Distribution distribution = DiscreteUniformDistribution.of(0, (tensor.length() - 1) * 3 + 1);
    for (int count = 0; count < 30; ++count) {
      Scalar index = RandomVariate.of(distribution).divide(RealScalar.of(3));
      Scalar scalar = interpolation.At(index);
      assertTrue(ExactScalarQ.of(scalar));
      assertEquals(Increment.ONE.apply(index), scalar);
      assertEquals(scalar, interpolation.get(Tensors.of(index)));
      assertEquals(scalar, interpolation.at(index));
    }
  }

  public void testFailScalar() {
    try {
      LinearInterpolation.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void test0D() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.empty());
    try {
      interpolation.get(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void test1D() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.vector(10, 20, 30, 40));
    StaticHelper.checkMatch(interpolation);
    StaticHelper.checkMatchExact(interpolation);
    StaticHelper.getScalarFail(interpolation);
  }

  public void test2D() {
    Distribution distribution = UniformDistribution.unit();
    Interpolation interpolation = LinearInterpolation.of(RandomVariate.of(distribution, 3, 5));
    StaticHelper.checkMatch(interpolation);
    StaticHelper.checkMatchExact(interpolation);
    StaticHelper.getScalarFail(interpolation);
  }

  public void testFailNull() {
    try {
      LinearInterpolation.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
