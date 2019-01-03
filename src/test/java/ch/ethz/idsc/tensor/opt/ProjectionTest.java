// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class ProjectionTest extends TestCase {
  public void testReal() {
    Tensor projection = Projection.on(Tensors.vector(1, 0, 0)).apply(Tensors.vector(1, 1, 1));
    assertEquals(projection, UnitVector.of(3, 0));
    Tensor p2 = Projection.on(Tensors.vector(1, 1, 1)).apply(Tensors.vector(5, 6, 7));
    assertEquals(p2, Tensors.vector(6, 6, 6));
    assertTrue(ExactScalarQ.all(p2));
  }

  public void testComplex() {
    Tensor p2 = Projection.on(Tensors.vector(1, 1, 1)).apply(Tensors.fromString("{5, I, 7}"));
    assertEquals(Tensors.fromString("{4 + I/3, 4 + I/3, 4 + I/3}"), p2);
    assertTrue(ExactScalarQ.all(p2));
  }

  public void testUV() {
    Tensor u = Tensors.fromString("{1 + I, 3 - 2*I}");
    Tensor v = Tensors.fromString("{2 - 4*I, 1 + 7*I}");
    Tensor m_uv = Tensors.fromString("{-(47/35) + (9*I)/35, 53/35 - (54*I)/35}");
    Tensor uv = Projection.of(u, v);
    assertEquals(uv, m_uv);
    assertTrue(ExactScalarQ.all(uv));
    Tensor pOnV_u = Projection.on(v).apply(u);
    assertEquals(pOnV_u, uv);
    assertTrue(ExactScalarQ.all(pOnV_u));
  }

  public void testVU() {
    Tensor u = Tensors.fromString("{1 + I, 3 - 2*I}");
    Tensor v = Tensors.fromString("{2 - 4*I, 1 + 7*I}");
    Tensor m_vu = Tensors.fromString("{-2 + (4*I)/15, -(1/3) + (77*I)/15}");
    Tensor vu = Projection.of(v, u);
    assertEquals(vu, m_vu);
    assertTrue(ExactScalarQ.all(vu));
    Tensor pOnU_v = Projection.on(u).apply(v);
    assertEquals(pOnU_v, vu);
    assertTrue(ExactScalarQ.all(pOnU_v));
  }

  public void testZeroFail() {
    try {
      Projection.on(Tensors.vector(0, 0, 0));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Projection.on(Tensors.vector(0.0, 0, 0));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Projection.on(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Projection.on(HilbertMatrix.of(3, 2));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
