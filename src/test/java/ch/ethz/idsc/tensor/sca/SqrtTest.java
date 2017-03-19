// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class SqrtTest extends TestCase {
  public void testNegative() {
    RealScalar n2 = RealScalar.of(-2);
    Scalar sr = Sqrt.function.apply(n2);
    assertEquals(Rationalize.of(sr.absSquared(), 10000), RealScalar.of(2));
    assertEquals(Rationalize.of(sr.multiply(sr), 10000), n2);
  }

  public void testComplex() {
    Scalar scalar = ComplexScalar.of(0, 2);
    Scalar root = Sqrt.function.apply(scalar);
    Scalar res = ComplexScalar.of(1, 1);
    assertEquals(Chop.of(root.subtract(res)), ZeroScalar.get());
  }

  public void testZero() {
    assertEquals(ZeroScalar.get(), Sqrt.function.apply(ZeroScalar.get()));
  }
}
