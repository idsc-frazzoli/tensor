// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class BSplineInterpolationTest extends TestCase {
  public void testVector() {
    Tensor tensor = Tensors.vector(2, 6, 4, 9, 10, 3);
    for (int degree = 0; degree < 4; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      for (int index = 0; index < tensor.length(); ++index) {
        Scalar scalar = interpolation.At(RealScalar.of(index));
        assertEquals(scalar, tensor.Get(index));
        ExactScalarQ.require(scalar);
      }
    }
  }

  public void testMatrix1() {
    Tensor tensor = HilbertMatrix.of(10, 5);
    for (int degree = 0; degree < 4; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      for (int index = 0; index < tensor.length(); ++index) {
        Tensor result = interpolation.at(RealScalar.of(index));
        assertEquals(result, tensor.get(index));
        Tensor svalue = interpolation.get(Tensors.vector(1, 2));
        assertEquals(svalue, RationalScalar.of(1, 4));
        Tensor vvalue = interpolation.get(Tensors.vector(3));
        assertEquals(vvalue, tensor.get(3));
      }
      try {
        interpolation.at(RealScalar.of(-0.1));
        fail();
      } catch (Exception exception) {
        // ---
      }
      try {
        interpolation.at(RealScalar.of(9.1));
        fail();
      } catch (Exception exception) {
        // ---
      }
    }
  }

  public void testMatrix2() {
    Tensor tensor = HilbertMatrix.of(5, 10);
    for (int degree = 0; degree < 4; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      for (int index = 0; index < tensor.length(); ++index) {
        Tensor result = interpolation.at(RealScalar.of(index));
        assertEquals(result, tensor.get(index));
      }
    }
  }

  public void testAd() {
    Tensor tensor = LieAlgebras.se2();
    for (int degree = 0; degree < 4; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      for (int index = 0; index < tensor.length(); ++index) {
        Tensor result = interpolation.at(RealScalar.of(index));
        assertEquals(result, tensor.get(index));
        Tensor svalue = interpolation.get(Tensors.vector(1, 2));
        assertEquals(svalue, UnitVector.of(3, 0).negate());
        ExactTensorQ.require(svalue);
      }
    }
  }

  public void testSolve() {
    Tensor interp = Tensors.vector(1, 0, 3, 2);
    for (int degree = 0; degree < 4; ++degree) {
      Tensor tensor = BSplineInterpolation.solve(degree, interp);
      assertTrue(ExactTensorQ.of(tensor));
      ScalarTensorFunction bSplineFunction = BSplineFunction.string(degree, tensor);
      for (int index = 0; index < interp.length(); ++index)
        assertEquals(bSplineFunction.apply(RealScalar.of(index)), interp.get(index));
    }
  }

  public void testLinear() {
    for (int n = 1; n < 6; ++n) {
      Tensor tensor = BSplineInterpolation.matrix(1, n);
      assertEquals(tensor, IdentityMatrix.of(n));
      assertTrue(ExactTensorQ.of(tensor));
    }
  }

  public void testQuadratic() {
    for (int n = 1; n <= 6; ++n) {
      Tensor vector = Array.of(l -> RealScalar.ONE, n);
      Tensor tensor = BSplineInterpolation.matrix(2, n);
      assertEquals(Dimensions.of(tensor), Arrays.asList(n, n));
      assertEquals(tensor.dot(vector), vector);
      assertTrue(ExactTensorQ.of(tensor));
    }
  }

  public void testCubic() {
    for (int n = 1; n <= 6; ++n) {
      Tensor vector = Array.of(l -> RealScalar.ONE, n);
      Tensor tensor = BSplineInterpolation.matrix(3, n);
      assertEquals(Dimensions.of(tensor), Arrays.asList(n, n));
      assertEquals(tensor.dot(vector), vector);
      assertTrue(ExactTensorQ.of(tensor));
    }
  }
}
