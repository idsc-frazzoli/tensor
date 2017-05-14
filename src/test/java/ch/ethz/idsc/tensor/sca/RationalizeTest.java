// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class RationalizeTest extends TestCase {
  public void testBasics() {
    assertEquals(Rationalize.of(RealScalar.of(Math.PI), 102).toString(), "311/99");
    assertEquals(Rationalize.of(RealScalar.of(2 / 3.), 1000).toString(), "2/3");
    assertEquals(Rationalize.of(RealScalar.of(-2 / 3.), 1000).toString(), "-2/3");
    assertEquals(Rationalize.of(RealScalar.of(0), 1000).toString(), "0");
    assertEquals(Rationalize.of(RealScalar.of(13), 1000).toString(), "13");
    assertEquals(Rationalize.of(RealScalar.of(-13), 1000).toString(), "-13");
    assertEquals(Rationalize.of(RealScalar.of(4), 1000), RealScalar.of(4));
    assertEquals(Rationalize.of(RealScalar.of(-4), 1000), RealScalar.of(-4));
    RealScalar tenth = RealScalar.of(0.1);
    assertEquals(Rationalize.of(tenth, 1000).toString(), "1/10");
    assertEquals(Rationalize.of(tenth, 6).toString(), "1/6");
    assertEquals(Rationalize.of(tenth.negate(), 6).toString(), "-1/6");
    assertEquals(Rationalize.of(tenth, 5).toString(), "1/5");
    assertEquals(Rationalize.of(tenth.negate(), 5), ZeroScalar.get());
    assertEquals(Rationalize.of(tenth, 4).toString(), "0");
  }

  public void testRoundConsistency() {
    Tensor s = Tensors.vectorDouble(-2.5, -2, -1.5, -1, -0.5, 0, 0.1, 0.5, 1, 1.5, 2, 2.5);
    List<Long> round = s.flatten(0) //
        .map(RealScalar.class::cast) //
        .map(RealScalar::number) //
        .map(Number::doubleValue) //
        .map(Math::round) //
        .collect(Collectors.toList());
    Tensor ratio = Rationalize.of(s, 1);
    // System.out.println(Rationalize.of(RealScalar.of(-11.5), 1));
    assertEquals(ratio, Tensors.vector(round));
  }

  private static void denCheck(RealScalar realScalar, long max) {
    final Tensor re = Rationalize.of(realScalar, max);
    if (re instanceof RationalScalar) {
      RationalScalar rs = (RationalScalar) re;
      assertTrue(rs.denominator().longValue() <= max);
    }
  }

  public void testDenominator() {
    Random r = new Random();
    for (int index = 0; index < 1000; ++index) {
      RealScalar realScalar = DoubleScalar.of(r.nextDouble() - 0.5);
      long max = r.nextInt(10_000_000);
      denCheck(realScalar, max);
    }
  }
}
