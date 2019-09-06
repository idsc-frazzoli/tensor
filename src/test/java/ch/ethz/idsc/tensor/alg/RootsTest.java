// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class RootsTest extends TestCase {
  private static final int LIMIT = 20;

  public void testConstantUniform() {
    Tensor roots = Roots.of(Tensors.vector(2));
    assertTrue(Tensors.isEmpty(roots));
  }

  public void testZeros() {
    Tensor roots = Roots.of(Tensors.vector(0, 0, 1, 0));
    assertEquals(roots, Array.zeros(2));
  }

  public void testUnitVector() {
    for (int length = 1; length < 10; ++length) {
      Tensor coeffs = UnitVector.of(length, length - 1);
      assertEquals(Roots.of(coeffs), Array.zeros(length - 1));
    }
  }

  public void testUnitVectorPlus() {
    for (int length = 1; length < 10; ++length) {
      Tensor coeffs = UnitVector.of(length + 3, length - 1);
      assertEquals(Roots.of(coeffs), Array.zeros(length - 1));
    }
  }

  public void testUniform5() {
    Distribution distribution = UniformDistribution.of(-5, 5);
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs = RandomVariate.of(distribution, length);
        if (Scalars.nonZero(Last.of(coeffs))) {
          Tensor roots = Roots.of(coeffs);
          VectorQ.requireLength(roots, length - 1);
          Tensor check = roots.map(Series.of(coeffs));
          if (!Chop._03.allZero(check)) {
            System.err.println("uni5 " + coeffs);
            System.err.println(check);
            fail();
          }
        } else
          System.out.println("skip " + coeffs);
      }
  }

  public void testUniform10() {
    Distribution distribution = UniformDistribution.of(-10, 10);
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs = RandomVariate.of(distribution, length);
        if (Scalars.nonZero(Last.of(coeffs))) {
          Tensor roots = Roots.of(coeffs);
          VectorQ.requireLength(roots, length - 1);
          Tensor check = roots.map(Series.of(coeffs));
          if (!Chop._03.allZero(check)) {
            System.err.println("uniT " + coeffs);
            System.err.println(check);
            fail();
          }
        } else
          System.out.println("skip " + coeffs);
      }
  }

  public void testNormal() {
    Distribution distribution = NormalDistribution.of(0, 0.3);
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs = RandomVariate.of(distribution, length);
        Tensor roots = Roots.of(coeffs);
        Tensor check = roots.map(Series.of(coeffs));
        Chop._04.requireAllZero(check);
      }
  }

  public void testRandomReal() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs = RandomVariate.of(distribution, length);
        Tensor roots = Roots.of(coeffs);
        ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
        Tensor tensor = roots.map(scalarUnaryOperator);
        boolean allZero = Chop._04.allZero(tensor);
        if (!allZero) {
          System.err.println(coeffs);
          System.err.println(tensor);
        }
        assertTrue(allZero);
      }
  }

  public void testRandomRealQuantity() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs = Array.of(list -> Quantity.of(RandomVariate.of(distribution), "m^-" + list.get(0)), length);
        Tensor roots = Roots.of(coeffs);
        ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
        Tensor tensor = roots.map(scalarUnaryOperator);
        boolean allZero = Chop._04.allZero(tensor);
        if (!allZero) {
          System.err.println(coeffs);
          System.err.println(tensor);
        }
        assertTrue(allZero);
      }
  }

  public void testRandomComplex() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs_re = RandomVariate.of(distribution, length);
        Tensor coeffs_im = RandomVariate.of(distribution, length);
        Tensor coeffs = coeffs_re.add(coeffs_im.multiply(ComplexScalar.I));
        Tensor roots = Roots.of(coeffs);
        ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
        Tensor tensor = roots.map(scalarUnaryOperator);
        boolean allZero = Chop._04.allZero(tensor);
        if (!allZero) {
          System.err.println(coeffs);
          System.err.println(tensor);
        }
        assertTrue(allZero);
      }
  }

  public void testRandomComplexQuantity() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 4; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor coeffs = Array.of(list -> Quantity.of(ComplexScalar.of( //
            RandomVariate.of(distribution), RandomVariate.of(distribution)), "m^-" + list.get(0)), length);
        Tensor roots = Roots.of(coeffs);
        ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
        Tensor tensor = roots.map(scalarUnaryOperator);
        boolean allZero = Chop._04.allZero(tensor);
        if (!allZero) {
          System.err.println(coeffs);
          System.err.println(tensor);
        }
        assertTrue(allZero);
      }
  }

  public void testRealUniqueRoots() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 3; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor roots = Sort.of(RandomVariate.of(distribution, length));
        Tensor solve = Roots.of(CoefficientList.of(roots));
        if (!Chop._03.close(roots, solve)) {
          System.err.println("real unique");
          System.err.println(roots);
          System.err.println(solve);
          fail();
        }
      }
  }

  public void testRealTripleRoot() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 3; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor zeros = ConstantArray.of(RandomVariate.of(distribution), length);
        Tensor roots = Roots.of(CoefficientList.of(zeros));
        if (!Chop._01.close(zeros, roots)) {
          System.err.println(zeros);
          fail();
        }
      }
  }

  public void testComplexTripleRoot() {
    Distribution distribution = NormalDistribution.standard();
    for (int length = 1; length <= 3; ++length)
      for (int index = 0; index < LIMIT; ++index) {
        Tensor zeros = ConstantArray.of(ComplexScalar.of( //
            RandomVariate.of(distribution), //
            RandomVariate.of(distribution)), length);
        Tensor roots = Roots.of(CoefficientList.of(zeros));
        for (int count = 0; count < length; ++count) {
          boolean anyZero = roots.map(zeros.Get(count)::subtract).stream() //
              .anyMatch(Chop._01::allZero);
          if (!anyZero) {
            System.err.println(zeros);
            System.err.println(roots);
            fail();
          }
        }
      }
  }
}
