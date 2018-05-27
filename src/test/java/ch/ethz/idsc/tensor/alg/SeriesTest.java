// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Mod;
import junit.framework.TestCase;

public class SeriesTest extends TestCase {
  public void testEmptyReal() {
    Scalar scalar = Series.of(Tensors.empty()).apply(RealScalar.of(2));
    assertEquals(scalar, RealScalar.ZERO);
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testEmptyGaussian() {
    Scalar scalar = Series.of(Tensors.empty()).apply(GaussScalar.of(4, 7));
    assertEquals(scalar, GaussScalar.of(0, 7));
    assertTrue(ExactScalarQ.of(scalar));
  }

  public void testHorner1() {
    Tensor coeffs = Tensors.vector(-3, 4);
    Scalar actual = Series.of(coeffs).apply(RealScalar.of(2));
    Scalar expected = RealScalar.of(-3 + 2 * 4);
    assertEquals(expected, actual);
  }

  public void testHorner2() {
    Tensor coeffs = Tensors.vector(-3, 4, -5);
    Scalar actual = Series.of(coeffs).apply(RealScalar.of(2));
    Scalar expected = RealScalar.of(-3 + 4 * (2) - 5 * (2 * 2));
    assertEquals(expected, actual);
  }

  public void testGauss() {
    Scalar scalar1 = Series.of(Tensors.of( //
        GaussScalar.of(2, 7), GaussScalar.of(4, 7), GaussScalar.of(5, 7))) //
        .apply(GaussScalar.of(6, 7));
    Scalar scalar2 = Series.of( //
        Tensors.vector(2, 4, 5)).apply(RealScalar.of(6));
    Scalar scalar3 = Mod.function(RealScalar.of(7)).apply(scalar2);
    assertEquals(scalar1.number().intValue(), scalar3.number().intValue());
  }

  public void testAccumulate() {
    Tensor coeffs = Tensors.vector(2, 1, -3, 2, 3, 0, 2);
    Tensor accumu = Accumulate.of(coeffs);
    assertEquals(Series.of(coeffs).apply(RealScalar.ONE), Total.of(coeffs));
    assertEquals(Last.of(accumu), Total.of(coeffs));
    for (int index = 1; index < coeffs.length(); ++index) {
      Scalar scalar = Series.of(coeffs.extract(index, coeffs.length())).apply(RealScalar.ONE);
      Scalar diff = (Scalar) Last.of(accumu).subtract(accumu.Get(index - 1));
      assertEquals(scalar, diff);
    }
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(-4, "m*s");
    Scalar qs2 = Quantity.of(3, "m");
    Scalar val = Quantity.of(2, "s");
    Scalar res = Series.of(Tensors.of(qs1, qs2)).apply(val);
    assertEquals(res.toString(), "2[m*s]");
  }
}
