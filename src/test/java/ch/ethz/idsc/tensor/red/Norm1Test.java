// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class Norm1Test extends TestCase {
  public void testQuantity1() {
    Scalar qs1 = Quantity.of(-3, "m");
    Scalar qs2 = Quantity.of(-4, "m");
    Scalar qs3 = Quantity.of(7, "m");
    Tensor vec = Tensors.of(qs1, qs2);
    assertEquals(Norm._1.ofVector(vec), qs3);
  }

  public void testQuantity2() {
    Tensor vec = Tensors.of( //
        Quantity.of(-3, "m"), //
        Quantity.of(0, "s*rad"), //
        RealScalar.ZERO, //
        Quantity.of(-4, "m") //
    );
    assertEquals(Norm._1.ofVector(vec), Quantity.of(7, "m"));
  }

  public void testScalarFail() {
    try {
      Norm._1.ofVector(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyStreamFail() {
    try {
      Norm1.ofVector(Stream.of());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
