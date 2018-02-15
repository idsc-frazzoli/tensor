// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Mod;
import junit.framework.TestCase;

public class MultinomialTest extends TestCase {
  public void testEmptyReal() {
    Scalar actual = Multinomial.horner(Tensors.empty(), RealScalar.of(2));
    assertEquals(actual, RealScalar.ZERO);
  }

  public void testEmptyGaussian() {
    Scalar actual = Multinomial.horner(Tensors.empty(), GaussScalar.of(4, 7));
    assertEquals(actual, GaussScalar.of(0, 7));
  }

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

  public void testGauss() {
    Scalar scalar1 = Multinomial.horner( //
        Tensors.of(GaussScalar.of(2, 7), GaussScalar.of(4, 7), GaussScalar.of(5, 7)), //
        GaussScalar.of(6, 7));
    Scalar scalar2 = Multinomial.horner( //
        Tensors.vector(2, 4, 5), RealScalar.of(6));
    Scalar scalar3 = Mod.function(RealScalar.of(7)).apply(scalar2);
    assertEquals(scalar1.number().intValue(), scalar3.number().intValue());
  }

  public void testAccumulate() {
    Tensor coeffs = Tensors.vector(2, 1, -3, 2, 3, 0, 2);
    Tensor accumu = Accumulate.of(coeffs);
    assertEquals(Multinomial.horner(coeffs, RealScalar.ONE), Total.of(coeffs));
    assertEquals(Last.of(accumu), Total.of(coeffs));
    for (int index = 1; index < coeffs.length(); ++index) {
      Scalar scalar = Multinomial.horner(coeffs.extract(index, coeffs.length()), RealScalar.ONE);
      Scalar diff = (Scalar) Last.of(accumu).subtract(accumu.Get(index - 1));
      assertEquals(scalar, diff);
    }
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(-4, "m*s");
    Scalar qs2 = Quantity.of(3, "m");
    Scalar val = Quantity.of(2, "s");
    Scalar res = Multinomial.horner(Tensors.of(qs1, qs2), val);
    assertEquals(res.toString(), "2[m*s]");
  }

  public void testDerivative() {
    Tensor coeffs = Tensors.vector(-3, 4, -5, 8, 1);
    Tensor result = Multinomial.derivative(coeffs);
    assertEquals(result, Tensors.vector(4, -5 * 2, 8 * 3, 1 * 4));
  }

  public void testDerivativeEmpty() {
    assertEquals(Multinomial.derivative(Tensors.vector()), Tensors.vector());
    assertEquals(Multinomial.derivative(Tensors.vector(3)), Tensors.empty());
  }

  public void testDerivativeScalarFail() {
    try {
      Multinomial.derivative(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
