// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class SqrtTest extends TestCase {
  public void testNegative() {
    RealScalar n2 = RealScalar.of(-2);
    Scalar sr = Sqrt.function.apply(n2);
    assertEquals(Rationalize.of(AbsSquared.function.apply(sr), 10000), RealScalar.of(2));
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

  public void testRational() {
    assertEquals(RationalScalar.of(16, 25).sqrt().toString(), "4/5");
    assertEquals(RationalScalar.of(-16, 25).sqrt().toString(), "0+4/5*I");
  }

  public void testReal() {
    assertEquals(RealScalar.of(16 / 25.).sqrt(), Scalars.fromString("4/5"));
    assertEquals(RealScalar.of(-16 / 25.).sqrt(), Scalars.fromString("0+4/5*I"));
  }

  public void testBigInteger() {
    BigInteger r = Sqrt.of(new BigInteger("21065681101554527729739161805139578084"));
    assertEquals(r, new BigInteger("4589736495873649578"));
    assertEquals(Sqrt.of(BigInteger.ONE), BigInteger.ONE);
    assertEquals(Sqrt.of(BigInteger.ZERO), BigInteger.ZERO);
    try {
      Sqrt.of(new BigInteger("21065681101554527729739161805139578083"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Sqrt.of(new BigInteger("-16"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
