// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MultinomialTest extends TestCase {
  public void testHorner1() {
    Tensor coeffs = Tensors.vector(-3, 4);
    Scalar actual = Multinomial.horner(coeffs, RealScalar.of(2));
    Scalar expected = RealScalar.of(-3 + 2 * 4);
    assertEquals(expected, actual);
  }

  public void testHorner2() {
    Tensor coeffs = Tensors.vector(-3, 4, -5);
    Scalar actual = Multinomial.horner(coeffs, RealScalar.of(2));
    Scalar expected = RealScalar.of(-3 + 4 * (2) - 5 * (2 * 2));
    assertEquals(expected, actual);
  }
}
