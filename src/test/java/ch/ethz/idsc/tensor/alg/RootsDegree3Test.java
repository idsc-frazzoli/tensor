// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class RootsDegree3Test extends TestCase {
  public void testSteer() {
    Scalar c = RealScalar.of(+0.8284521034333863);
    Scalar a = RealScalar.of(-0.33633373640449604);
    Tensor coeffs = Tensors.of(RealScalar.ZERO, c, RealScalar.ZERO, a);
    ScalarUnaryOperator cubic = Series.of(coeffs);
    Tensor roots = Roots.of(Tensors.of(RealScalar.ZERO, c, RealScalar.ZERO, a));
    Chop.NONE.requireAllZero(Imag.of(roots));
    Chop._13.requireAllZero(roots.get(1));
    Chop._13.requireAllZero(roots.map(cubic));
  }

  public void testCubicChallenge2() {
    Tensor coeffs = Tensors.vector(1.5583019232667707, 0.08338030361650195, 0.5438230916311243, 1.1822223716596811);
    ScalarUnaryOperator polynomial = Series.of(coeffs);
    // roots obtained by Mathematica:
    Scalar cR = RealScalar.of(-1.2487729899770943);
    Chop._12.requireAllZero(polynomial.apply(cR));
    Scalar cP = ComplexScalar.of(0.3943861563603456, +0.9486756887529066);
    Scalar cN = ComplexScalar.of(0.3943861563603456, -0.9486756887529066);
    Chop._12.requireAllZero(polynomial.apply(cP));
    Chop._12.requireAllZero(polynomial.apply(cN));
    Tensor roots = Roots.of(coeffs);
    assertTrue(roots.stream().map(root -> root.subtract(cR)).anyMatch(Chop._12::allZero));
    assertTrue(roots.stream().map(root -> root.subtract(cP)).anyMatch(Chop._12::allZero));
    assertTrue(roots.stream().map(root -> root.subtract(cN)).anyMatch(Chop._12::allZero));
    Tensor rootz = RootsDegree3Full.of(coeffs);
    assertTrue(rootz.stream().map(root -> root.subtract(cR)).anyMatch(Chop._07::allZero));
    assertTrue(rootz.stream().map(root -> root.subtract(cP)).anyMatch(Chop._07::allZero));
    assertTrue(rootz.stream().map(root -> root.subtract(cN)).anyMatch(Chop._07::allZero));
  }

  public void testRoots3() {
    Tensor roots = Tensors.vector(0.27349919995262256, 0.28215588800565544, 0.3056009752969802);
    Tensor coeffs = CoefficientList.of(roots);
    Chop._09.requireClose(roots, Sort.of(RootsDegree3Full.of(coeffs)));
    Chop._12.requireClose(roots, Sort.of(RootsDegree3.of(coeffs)));
  }

  public void testTriple1() {
    // {0.22765732048577852, 0.22765732048577852, 0.22765732048577852}
    Tensor roots = Tensors.vector(2.146361758590232, 2.146361758590232, 2.146361758590232);
    Tensor coeffs = CoefficientList.of(roots);
    Tensor r1 = RootsDegree3Full.of(coeffs);
    Tensor r2 = RootsDegree3.of(coeffs);
    Chop._12.requireClose(roots, r1);
    Chop._12.requireClose(roots, r2);
  }

  public void testTriple2() {
    Tensor roots = Tensors.vector(0.22765732048577852, 0.22765732048577852, 0.22765732048577852);
    Tensor coeffs = CoefficientList.of(roots);
    Tensor r1 = RootsDegree3Full.of(coeffs);
    Tensor r2 = RootsDegree3.of(coeffs);
    Chop._12.requireClose(roots, r1);
    Chop._12.requireClose(roots, r2);
  }
}
