// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Map;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class RootsCubicTest extends TestCase {
  private static final int LIMIT = 20;

  public void testCubic() {
    Tensor coeffs = Tensors.vector(2, 3, 4, 5);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._13.allZero(tensor));
  }

  public void testMonomial() {
    Tensor coeffs = Tensors.vector(0, 0, 0, 10);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
  }

  public void testMonomialShiftedP() {
    Tensor coeffs = Tensors.vector(1, 3, 3, 1);
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, Tensors.vector(-1, -1, -1));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
  }

  public void testMonomialShiftedN() {
    Tensor coeffs = Tensors.vector(1, -3, 3, -1);
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, Tensors.vector(1, 1, 1));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
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
  }

  public void testCubicQuantity() {
    Tensor coeffs = Tensors.fromString("{2[m^-5], 3[m^-4], 4[m^-3], 5[m^-2]}");
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._13.allZero(tensor));
  }

  public void testCubicNumerics() {
    Tensor coeffs = Tensors.vector(0.7480756509468256, -0.11264914570345713, 0.5215628590156208, -0.8016542468533115);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._05.allZero(tensor));
  }

  public void testCubicRandomReal() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor coeffs = RandomVariate.of(distribution, 4);
      Tensor roots = Roots.of(coeffs);
      ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
      Tensor tensor = roots.map(scalarUnaryOperator);
      boolean allZero = Chop._04.allZero(tensor);
      if (!allZero) {
        System.out.println(coeffs);
        System.out.println(tensor);
      }
      assertTrue(allZero);
    }
  }

  public void testCubicRandomRealQuantity() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor coeffs = Array.of(list -> Quantity.of(RandomVariate.of(distribution), "m^-" + list.get(0)), 4);
      Tensor roots = Roots.of(coeffs);
      ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
      Tensor tensor = roots.map(scalarUnaryOperator);
      boolean allZero = Chop._04.allZero(tensor);
      if (!allZero) {
        System.out.println(coeffs);
        System.out.println(tensor);
      }
      assertTrue(allZero);
    }
  }

  public void testCubicRandomComplex() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor coeffs_re = RandomVariate.of(distribution, 4);
      Tensor coeffs_im = RandomVariate.of(distribution, 4);
      Tensor coeffs = coeffs_re.add(coeffs_im.multiply(ComplexScalar.I));
      Tensor roots = Roots.of(coeffs);
      ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
      Tensor tensor = roots.map(scalarUnaryOperator);
      boolean allZero = Chop._04.allZero(tensor);
      if (!allZero) {
        System.out.println(coeffs);
        System.out.println(tensor);
      }
      assertTrue(allZero);
    }
  }

  public void testCubicRandomComplexQuantity() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor coeffs = Array.of(list -> Quantity.of(ComplexScalar.of( //
          RandomVariate.of(distribution), RandomVariate.of(distribution)), "m^-" + list.get(0)), 4);
      Tensor roots = Roots.of(coeffs);
      ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
      Tensor tensor = roots.map(scalarUnaryOperator);
      boolean allZero = Chop._04.allZero(tensor);
      if (!allZero) {
        System.out.println(coeffs);
        System.out.println(tensor);
      }
      assertTrue(allZero);
    }
  }

  public void testRealUniqueRoots() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor zeros = Sort.of(RandomVariate.of(distribution, 3));
      Tensor roots = Roots.of(TestHelper.polynomial(zeros));
      if (!Chop._08.close(zeros, roots)) {
        System.err.println(zeros);
        fail();
      }
    }
  }

  public void testRealTripleRoot() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor zeros = ConstantArray.of(RandomVariate.of(distribution), 3);
      Tensor roots = Roots.of(TestHelper.polynomial(zeros));
      if (!Chop._08.close(zeros, roots)) {
        System.err.println(zeros);
        fail();
      }
    }
  }

  public void testComplexTripleRoot() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < LIMIT; ++index) {
      Tensor zeros = ConstantArray.of(ComplexScalar.of( //
          RandomVariate.of(distribution), //
          RandomVariate.of(distribution)), 3);
      Tensor roots = Roots.of(TestHelper.polynomial(zeros));
      for (int count = 0; count < 3; ++count) {
        boolean anyZero = roots.map(zeros.Get(count)::subtract).stream() //
            .anyMatch(Chop._03::allZero);
        if (!anyZero) {
          System.out.println(zeros);
          System.out.println(roots);
          fail();
        }
      }
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

  public void testCubicChallenge2() {
    Tensor coeffs = Tensors.vector(1.5583019232667707, 0.08338030361650195, 0.5438230916311243, 1.1822223716596811);
    Tensor roots = RootsCubic.of(coeffs);
    ScalarUnaryOperator series = Series.of(coeffs);
    // Tensor zeros =
    roots.map(series);
    // System.out.println(zeros);
  }
}
