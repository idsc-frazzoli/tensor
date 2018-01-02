// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.lie.CirclePoints;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class BSplineFunctionTest extends TestCase {
  public void testConstant() {
    ScalarTensorFunction stf = BSplineFunction.of(0, Tensors.vector(2, 1, 5, 0, -2));
    assertEquals(stf.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(stf.apply(RealScalar.of(0.3333)), RealScalar.of(2));
    assertEquals(stf.apply(RealScalar.of(0.5)), RealScalar.of(1));
    assertEquals(stf.apply(RealScalar.of(1.25)), RealScalar.of(1));
    assertEquals(stf.apply(RealScalar.of(1.49)), RealScalar.of(1));
    assertEquals(stf.apply(RealScalar.of(1.75)), RealScalar.of(5));
    assertEquals(stf.apply(RealScalar.of(2)), RealScalar.of(5));
    assertEquals(stf.apply(RealScalar.of(2.50)), RealScalar.of(0));
    assertEquals(stf.apply(RealScalar.of(3)), RealScalar.of(0));
    assertEquals(stf.apply(RealScalar.of(3.5)), RealScalar.of(-2));
    assertEquals(stf.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testLinear() {
    ScalarTensorFunction stf = BSplineFunction.of(1, Tensors.vector(2, 1, 5, 0, -2));
    assertEquals(stf.apply(RealScalar.of(0)), RealScalar.of(2));
    assertEquals(stf.apply(RationalScalar.of(1, 2)), RationalScalar.of(3, 2));
    assertEquals(stf.apply(RealScalar.of(1)), RealScalar.of(1));
    assertEquals(stf.apply(RealScalar.of(1.25)), RealScalar.of(2));
    assertEquals(stf.apply(RealScalar.of(1.50)), RealScalar.of(3));
    assertEquals(stf.apply(RealScalar.of(1.75)), RealScalar.of(4));
    assertEquals(stf.apply(RealScalar.of(2)), RealScalar.of(5));
    assertEquals(stf.apply(RealScalar.of(2.50)), RealScalar.of(2.5));
    assertEquals(stf.apply(RealScalar.of(3)), RealScalar.of(0));
    assertEquals(stf.apply(RealScalar.of(3.5)), RealScalar.of(-1));
    assertEquals(stf.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testLinearCurve() {
    Tensor c = Tensors.fromString("{{2,3}, {1,0}, {5,7}, {0,0},{-2,3}}");
    assertTrue(MatrixQ.of(c));
    BSplineFunction bSplineFunction = BSplineFunction.of(1, c);
    assertEquals(bSplineFunction.apply(RealScalar.of(1.5)), Tensors.vector(3, 3.5));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), Tensors.vector(-2, 3));
  }

  @SuppressWarnings("unused")
  public void testQuadratic() {
    BSplineFunction bSplineFunction = BSplineFunction.of(2, Tensors.vector(2, 1, 5, 0, -2));
    Tensor r = bSplineFunction.apply(RealScalar.of(0));
    // System.out.println(r);
    // assertEquals(r, RealScalar.of(2));
  }

  @SuppressWarnings("unused")
  public void testQuadraticSymmetry() {
    BSplineFunction bSplineFunction = BSplineFunction.of(2, Tensors.vector(0, 1, 2, 3));
    Tensor r1 = bSplineFunction.apply(RealScalar.of(0.5));
    Tensor r2 = bSplineFunction.apply(RealScalar.of(1.5));
    Tensor r3 = bSplineFunction.apply(RealScalar.of(2.5)); // does not evaluate correctly
    // System.out.println(r1);
    // System.out.println(r2);
    // System.out.println(r3);
    // assertEquals(r, RealScalar.of(2));
  }

  public void testCubic() {
    BSplineFunction bSplineFunction = BSplineFunction.of(3, Tensors.vector(2, 1, 5, 0, -2));
    Tensor r = bSplineFunction.apply(RealScalar.of(0));
    assertEquals(r, RealScalar.of(2));
  }

  public void testCubicLinear() {
    BSplineFunction bSplineFunction = BSplineFunction.of(3, Tensors.vector(2, 1, 0, -1, -2));
    assertEquals(bSplineFunction.apply(RealScalar.of(0)), RealScalar.of(2));
    // assertEquals(bSplineFunction.apply(RealScalar.of(1)), RealScalar.of(1));
    assertEquals(bSplineFunction.apply(RealScalar.of(2)), RealScalar.of(0));
    assertTrue(Chop._12.close(bSplineFunction.apply(RealScalar.of(3.999999999999)), RealScalar.of(-2)));
    assertEquals(bSplineFunction.apply(RealScalar.of(4)), RealScalar.of(-2));
  }

  public void testCirclePoints() {
    BSplineFunction bSplineFunction = BSplineFunction.of(3, CirclePoints.of(10));
    Tensor polygon = Subdivide.of(0, 9, 20).map(bSplineFunction::apply);
    assertTrue(MatrixQ.of(polygon));
  }

  public void testSymmetric() {
    Tensor c = Tensors.vector(1, 5, 3, -1, 0);
    BSplineFunction bs3f = BSplineFunction.of(3, c);
    BSplineFunction bs3r = BSplineFunction.of(3, Reverse.of(c));
    Tensor res1f = Subdivide.of(0, 4, 10).map(bs3f);
    Tensor res1r = Subdivide.of(4, 0, 10).map(bs3r);
    assertEquals(res1f, res1r);
  }

  public void testSerializable() throws Exception {
    BSplineFunction bSplineFunction = BSplineFunction.of(3, Tensors.vector(2, 1, 0, -1, -2));
    BSplineFunction copy = Serialization.copy(bSplineFunction);
    copy.apply(RealScalar.of(.3));
  }

  public void testOutside() {
    BSplineFunction bSplineFunction = BSplineFunction.of(3, Tensors.vector(2, 1, 0, -1, -2));
    bSplineFunction.apply(RealScalar.of(4));
    try {
      bSplineFunction.apply(RealScalar.of(-0.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      bSplineFunction.apply(RealScalar.of(5.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      bSplineFunction.apply(RealScalar.of(4.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
