// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.lie.CirclePoints;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.QuantityMagnitude;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;
import junit.framework.TestCase;

// cubic basis functions over unit interval [0, 1]
// {(1 - t)^3, 4 - 6 t^2 + 3 t^3, 1 + 3 t + 3 t^2 - 3 t^3, t^3}/6
public class BSplineFunctionStringTest extends TestCase {
  public void testConstant() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(0, Tensors.vector(2, 1, 5, 0, -2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0.3333)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0.5)), RealScalar.of(1));
    assertEquals(bSplineFunction.apply(RealScalar.of(1.25)), RealScalar.of(1));
    assertEquals(bSplineFunction.apply(RealScalar.of(1.49)), RealScalar.of(1));
    assertEquals(bSplineFunction.apply(RealScalar.of(1.75)), RealScalar.of(5));
    assertEquals(bSplineFunction.apply(RealScalar.of(2)), RealScalar.of(5));
    assertEquals(bSplineFunction.apply(RealScalar.of(2.50)), RealScalar.of(0));
    assertEquals(bSplineFunction.apply(RealScalar.of(3)), RealScalar.of(0));
    assertEquals(bSplineFunction.apply(RealScalar.of(3.5)), RealScalar.of(-2));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testLinear() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(1, Tensors.vector(2, 1, 5, 0, -2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RationalScalar.of(1, 2)), RationalScalar.of(3, 2));
    assertEquals(bSplineFunction.apply(RealScalar.of(1)), RealScalar.of(1));
    assertEquals(bSplineFunction.apply(RealScalar.of(1.25)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RealScalar.of(1.50)), RealScalar.of(3));
    assertEquals(bSplineFunction.apply(RealScalar.of(1.75)), RealScalar.of(4));
    assertEquals(bSplineFunction.apply(RealScalar.of(2)), RealScalar.of(5));
    assertEquals(bSplineFunction.apply(RealScalar.of(2.50)), RealScalar.of(2.5));
    assertEquals(bSplineFunction.apply(RealScalar.of(3)), RealScalar.of(0));
    assertEquals(bSplineFunction.apply(RealScalar.of(3.5)), RealScalar.of(-1));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testLinearCurve() {
    Tensor control = Tensors.fromString("{{2, 3}, {1, 0}, {5, 7}, {0, 0}, {-2, 3}}");
    assertTrue(MatrixQ.of(control));
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(1, control);
    assertEquals(bSplineFunction.apply(RealScalar.of(1.5)), Tensors.vector(3, 3.5));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), Tensors.vector(-2, 3));
  }

  public void testQuadratic() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(2, Tensors.vector(2, 1, 5, 0, -2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RealScalar.of(1)), RationalScalar.of(5, 3));
    assertEquals(bSplineFunction.apply(RealScalar.of(2)), RationalScalar.of(31, 8));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testQuadraticSymmetry() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(2, Tensors.vector(0, 1, 2, 3));
    Tensor r1 = bSplineFunction.apply(RealScalar.of(0.5));
    Tensor r2 = bSplineFunction.apply(RealScalar.of(1.5));
    Tensor r3 = bSplineFunction.apply(RealScalar.of(2.5)); // does not evaluate correctly
    assertTrue(Chop._12.close(r1, RealScalar.of(1 / 3.0)));
    assertEquals(r2, RealScalar.of(1.5));
    assertTrue(Chop._12.close(r3, RealScalar.of(8 / 3.0)));
  }

  public void testCubic() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, Tensors.vector(2, 1, 5, 0, -2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RationalScalar.HALF), RationalScalar.of(173, 96));
    assertEquals(bSplineFunction.apply(RealScalar.of(1)), RationalScalar.of(23, 12));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testCubicLinear() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, Tensors.vector(2, 1, 0, -1, -2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(bSplineFunction.apply(RealScalar.of(1)), RationalScalar.of(13, 12));
    assertEquals(bSplineFunction.apply(RealScalar.of(2)), RealScalar.of(0));
    assertTrue(Chop._12.close(bSplineFunction.apply(RealScalar.of(3.999999999999)), RealScalar.of(-2)));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testCirclePoints() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, CirclePoints.of(10));
    Tensor polygon = Subdivide.of(0, 9, 20).map(bSplineFunction::apply);
    assertTrue(MatrixQ.of(polygon));
  }

  public void testSymmetric() {
    Tensor control = Tensors.vector(1, 5, 3, -1, 0);
    int n = control.length() - 1;
    for (int degree = 0; degree <= 5; ++degree) {
      ScalarTensorFunction bsf = BSplineFunction.string(degree, control);
      ScalarTensorFunction bsr = BSplineFunction.string(degree, Reverse.of(control));
      Tensor res1f = Subdivide.of(0, n, 10).map(bsf);
      Tensor res1r = Subdivide.of(n, 0, 10).map(bsr);
      assertEquals(res1f, res1r);
    }
  }

  public void testQuantity() {
    Tensor control = Tensors.fromString("{2[m], 7[m], 3[m]}");
    Clip clip = Clips.interval(2, 7);
    int n = control.length() - 1;
    for (int degree = 0; degree <= 5; ++degree) {
      ScalarTensorFunction bSplineFunction = BSplineFunction.string(degree, control);
      Tensor tensor = Subdivide.of(0, n, 10).map(bSplineFunction);
      VectorQ.require(tensor);
      Tensor nounit = tensor.map(QuantityMagnitude.SI().in("m"));
      assertTrue(ExactTensorQ.of(nounit));
      nounit.map(clip::requireInside);
    }
  }

  public void testSingleton() {
    for (int degree = 0; degree < 4; ++degree) {
      ScalarTensorFunction bSplineFunction = BSplineFunction.string(degree, Tensors.vector(99));
      assertEquals(bSplineFunction.apply(RealScalar.ZERO), RealScalar.of(99));
      assertEquals(bSplineFunction.apply(RealScalar.of(0.0)), RealScalar.of(99));
    }
  }

  public void testIndex() {
    for (int degree = 0; degree <= 5; ++degree)
      for (int length = 2; length <= 6; ++length) {
        ScalarTensorFunction bSplineFunction = BSplineFunction.string(degree, IdentityMatrix.of(length));
        for (Tensor _x : Subdivide.of(0, length - 1, 13)) {
          Tensor tensor = bSplineFunction.apply(_x.Get());
          assertTrue(tensor.stream().map(Scalar.class::cast).allMatch(Clips.unit()::isInside));
          assertEquals(Total.of(tensor), RealScalar.ONE);
          assertTrue(ExactTensorQ.of(tensor));
        }
      }
  }

  public void testSerializable() throws Exception {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, Tensors.vector(2, 1, 0, -1, -2));
    Tensor value0 = bSplineFunction.apply(RealScalar.of(2.3));
    ScalarTensorFunction copy = Serialization.copy(bSplineFunction);
    Tensor value1 = copy.apply(RealScalar.of(2.3));
    assertEquals(value0, value1);
  }

  public void testSymmetry() {
    Distribution distribution = DiscreteUniformDistribution.of(-4, 7);
    int n = 20;
    Tensor domain = Subdivide.of(0, n - 1, 31);
    for (int degree = 1; degree < 8; ++degree) {
      Tensor control = RandomVariate.of(distribution, n, 3);
      ScalarTensorFunction mapForward = BSplineFunction.string(degree, control);
      Tensor forward = domain.map(mapForward);
      ScalarTensorFunction mapReverse = BSplineFunction.string(degree, Reverse.of(control));
      Tensor reverse = Reverse.of(domain.map(mapReverse));
      assertEquals(forward, reverse);
      assertTrue(ExactTensorQ.of(forward));
      assertTrue(ExactTensorQ.of(reverse));
    }
  }

  public void testBasisWeights1a() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(1, UnitVector.of(3, 1));
    Tensor limitMask = Range.of(1, 2).map(bSplineFunction);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1}"));
  }

  public void testBasisWeights2() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(2, UnitVector.of(5, 2));
    Tensor limitMask = Range.of(1, 4).map(bSplineFunction);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/8, 3/4, 1/8}"));
  }

  public void testBasisWeights3a() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, UnitVector.of(7, 3));
    Tensor limitMask = Range.of(2, 5).map(bSplineFunction);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/6, 2/3, 1/6}"));
  }

  public void testBasisWeights3b() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, UnitVector.of(5, 2));
    Tensor limitMask = Range.of(1, 4).map(bSplineFunction);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/6, 2/3, 1/6}"));
  }

  public void testBasisWeights4() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(4, UnitVector.of(9, 4));
    Tensor limitMask = Range.of(2, 7).map(bSplineFunction);
    assertEquals(Total.of(limitMask), RealScalar.ONE);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/384, 19/96, 115/192, 19/96, 1/384}"));
  }

  public void testBasisWeights5a() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(5, UnitVector.of(11, 5));
    Tensor limitMask = Range.of(3, 8).map(bSplineFunction);
    assertEquals(Total.of(limitMask), RealScalar.ONE);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/120, 13/60, 11/20, 13/60, 1/120}"));
  }

  public void testBasisWeights5b() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(5, UnitVector.of(9, 4));
    Tensor limitMask = Range.of(2, 7).map(bSplineFunction);
    assertEquals(Total.of(limitMask), RealScalar.ONE);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/120, 13/60, 11/20, 13/60, 1/120}"));
  }

  public void testBasisWeights5c() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(5, UnitVector.of(7, 3));
    Tensor limitMask = Range.of(1, 6).map(bSplineFunction);
    assertEquals(Total.of(limitMask), RealScalar.ONE);
    assertTrue(ExactTensorQ.of(limitMask));
    assertEquals(limitMask, Tensors.fromString("{1/120, 13/60, 11/20, 13/60, 1/120}"));
  }

  public void testEmptyFail() {
    for (int degree = -2; degree <= 4; ++degree)
      try {
        BSplineFunction.string(degree, Tensors.empty());
        fail();
      } catch (Exception exception) {
        // ---
      }
  }

  public void testNegativeFail() {
    try {
      BSplineFunction.string(-1, Tensors.vector(1, 2, 3, 4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testOutsideFail() {
    ScalarTensorFunction bSplineFunction = BSplineFunction.string(3, Tensors.vector(2, 1, 0, -1, -2));
    bSplineFunction.apply(RealScalar.of(4));
    try {
      bSplineFunction.apply(RealScalar.of(-0.1));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      bSplineFunction.apply(RealScalar.of(5.1));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      bSplineFunction.apply(RealScalar.of(4.1));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
