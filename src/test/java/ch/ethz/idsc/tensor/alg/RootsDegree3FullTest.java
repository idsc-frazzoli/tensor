// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Map;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class RootsDegree3FullTest extends TestCase {
  public void testCubic() {
    Tensor coeffs = Tensors.vector(2, 3, 4, 5);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._13.allZero(tensor));
    Tensor depres = RootsDegree3.of(coeffs);
    Chop._10.requireClose(depres, roots);
  }

  public void testMonomial() {
    Tensor coeffs = Tensors.vector(0, 0, 0, 10);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
    Tensor depres = RootsDegree3.of(coeffs);
    Chop._10.requireClose(depres, roots);
  }

  public void testMonomialShiftedP() {
    Tensor coeffs = Tensors.vector(1, 3, 3, 1);
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, Tensors.vector(-1, -1, -1));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
    Tensor depres = RootsDegree3.of(coeffs);
    Chop._10.requireClose(depres, roots);
  }

  public void testMonomialShiftedN() {
    Tensor coeffs = Tensors.vector(1, -3, 3, -1);
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, Tensors.vector(1, 1, 1));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
    Tensor depres = RootsDegree3.of(coeffs);
    Chop._10.requireClose(depres, roots);
  }

  public void testMonomialQuadShift() {
    Tensor coeffs = Tensors.vector(1, 1, -1, -1);
    Tensor roots = Roots.of(coeffs);
    Map<Tensor, Long> map = Tally.of(roots);
    assertEquals((long) map.get(RealScalar.ONE), 1);
    assertEquals((long) map.get(RealScalar.ONE.negate()), 2);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._07.allZero(tensor));
    Tensor depres = RootsDegree3.of(coeffs);
    Chop._10.requireClose(depres, roots);
  }

  public void testCubicQuantity() {
    Tensor coeffs = Tensors.fromString("{2[m^-5], 3[m^-4], 4[m^-3], 5[m^-2]}");
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._13.allZero(tensor));
    Tensor depres = RootsDegree3.of(coeffs);
    Chop._10.requireClose(depres, roots);
  }

  public void testCubicNumerics() {
    Tensor coeffs = Tensors.vector(0.7480756509468256, -0.11264914570345713, 0.5215628590156208, -0.8016542468533115);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._05.allZero(tensor));
    Tensor depres = RootsDegree3.of(coeffs);
    if (!Chop._10.close(depres, roots)) {
      System.out.println(depres);
      System.out.println(roots);
      fail();
    }
  }

  public void testCubicChallenge() {
    Tensor coeffs = Tensors.vector(1.8850384838238452, -0.07845356111460325, -0.6128180724984655, -1.5845220466594934);
    Tensor roots = Roots.of(coeffs);
    Scalar m0 = Scalars.fromString("-0.6590816994450482 - 0.9180824258012533 * I");
    Scalar m1 = Scalars.fromString("-0.6590816994450482 + 0.9180824258012533 * I");
    Scalar m2 = RealScalar.of(0.9314107665802999);
    assertTrue(roots.stream().map(scalar -> scalar.subtract(m0)).anyMatch(Chop._05::allZero));
    assertTrue(roots.stream().map(scalar -> scalar.subtract(m1)).anyMatch(Chop._05::allZero));
    assertTrue(roots.stream().map(scalar -> scalar.subtract(m2)).anyMatch(Chop._05::allZero));
  }
  // {4.403491745360149, -3.6065243114260417, -4.588031155616683, -4.8648946627594114E-4}
}
