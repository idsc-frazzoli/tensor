// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import junit.framework.TestCase;

public class LinearInterpolationTest extends TestCase {
  public void testEmpty() {
    Tensor tensor = Tensors.vector(10, 20, 30, 40);
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Tensor res = interpolation.get(Tensors.empty());
    assertEquals(res, tensor);
  }

  public void testSimple() {
    Interpolation interpolation = LinearInterpolation.of(Tensors.vector(10, 20, 30, 40));
    Tensor res = interpolation.get(Tensors.vector(2));
    assertEquals(res, RealScalar.of(30));
  }

  public void testMatrix() {
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

  public void testRank3() {
    Tensor arr = Array.of(Tensors::vector, 2, 3);
    Interpolation interpolation = LinearInterpolation.of(arr);
    // System.out.println(Dimensions.of(arr));
    // Tensor res =
    interpolation.get(Tensors.vector(0.3, 1.8, 0.3));
    // System.out.println(res);
    // res.append(null);
  }
}
