// code by jph
package ch.ethz.idsc.tensor.num;

import java.io.IOException;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ExtendedGCDTest extends TestCase {
  static boolean isConsistent(Tensor vector, ExtendedGCD extendedGCD) {
    return vector.dot(extendedGCD.factors()).equals(extendedGCD.gcd());
  }

  public void testSimple() throws ClassNotFoundException, IOException {
    Function<Tensor, ExtendedGCD> function = ExtendedGCD.function(RealScalar.ONE);
    Tensor vector = Tensors.vector(12334 * 5, 32332 * 5);
    ExtendedGCD extendedGCD = Serialization.copy(function.apply(vector));
    assertTrue(isConsistent(vector, extendedGCD));
  }
}
