// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
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

  public void testSimple() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.vector(10, 20, 30, 40));
    assertEquals(interpolation.get(Tensors.vector(0)), RealScalar.of(10));
    assertEquals(interpolation.get(Tensors.vector(2)), RealScalar.of(30));
    assertEquals(interpolation.get(Tensors.vector(2.5)), RealScalar.of(35));
    assertEquals(interpolation.get(Tensors.vector(3)), RealScalar.of(40));
  }

  public void testMatrix1() {
    Tensor tensor = Tensors.matrix(new Number[][] { //
        { 5, 5, 5 }, //
        { 1, 10, 100 } //
    });
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
    Tensor tensor = Tensors.matrix(new Number[][] { //
        { 5, 5, 5 }, //
        { 1, 10, 100 } //
    });
    Interpolation interpolation = LinearInterpolation.of(tensor);
    assertEquals(interpolation.get(Tensors.vector(1)), Tensors.vector(1, 10, 100));
    assertEquals(interpolation.get(Tensors.vector(1, 2)), RealScalar.of(100));
    assertEquals(interpolation.get(Tensors.vector(0)), Tensors.vector(5, 5, 5));
    assertEquals(interpolation.get(Tensors.vector(1, 0)), RealScalar.of(1));
    assertEquals(interpolation.get(Tensors.vector(0, 0)), RealScalar.of(5));
  }

  public void testRank3() {
    Tensor arr = Array.of(Tensors::vector, 2, 3);
    Interpolation interpolation = LinearInterpolation.of(arr);
    // System.out.println(Dimensions.of(arr));
    // Tensor res =
    interpolation.get(Tensors.vector(0.3, 1.8, 0.3));
    // System.out.println(res);
    // res.append(null);
  }

  public void testFail() {
    try {
      LinearInterpolation.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
