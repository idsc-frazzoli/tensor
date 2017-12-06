// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class VectorAngleTest extends TestCase {
  public void testReal1() {
    Tensor id = IdentityMatrix.of(5);
    Scalar s1 = VectorAngle.of(id.get(2).multiply(RealScalar.of(3)), id.get(4)).get();
    assertEquals(s1.toString(), "1.5707963267948966");
    Scalar s2 = VectorAngle.of(id.get(2), id.get(2)).get();
    assertEquals(s2, RealScalar.ZERO);
  }

  public void testReal2() {
    Tensor u = Tensors.vector(1, 2, 3);
    Tensor v = Tensors.vector(-6, -3, 2);
    Scalar s1 = VectorAngle.of(u, v).get();
    assertEquals(s1.toString(), "1.8019298645941293"); // mathematica
  }

  public void testComplex() {
    Tensor u = Tensors.fromString("{0+1*I,3/4-1*I}");
    Tensor v = Tensors.fromString("{1+1*I,-1/2+2*I}");
    Scalar s1 = VectorAngle.of(u, v).get();
    assertEquals(s1.toString(), "1.921525068221019"); // mathematica
  }

  public void testComplex2() {
    Tensor u = Tensors.fromString("{0.6246950475544243*I, 0.4685212856658182-0.6246950475544243*I}");
    Tensor v = Tensors.fromString("{0.4+0.4*I, -0.2+0.8*I}");
    // mathematica gives 1.9215250682210188` - 2.8189256484623115`*^-17 I +
    Scalar s1 = VectorAngle.of(u, v).get();
    assertTrue(s1 instanceof RealScalar);
    assertTrue(Chop._14.close(s1, Scalars.fromString("1.921525068221019")));
  }

  public void testLarge() {
    Tensor u = Tensors.vector(1e300, 0);
    Tensor v = Tensors.vector(1e300, 1e300);
    assertTrue(Chop._14.close(VectorAngle.of(u, v).get(), DoubleScalar.of(0.7853981633974484)));
  }

  public void testSmall() {
    Tensor u = Tensors.vector(1e-300, 0);
    Tensor v = Tensors.vector(-1e-300, -1e-300);
    assertTrue(Chop._14.close(VectorAngle.of(u, v).get(), DoubleScalar.of(2.356194490192345)));
  }

  public void testFail() {
    try {
      VectorAngle.of(HilbertMatrix.of(3), HilbertMatrix.of(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSingle() {
    assertEquals(VectorAngle.of(Tensors.vector(1), Tensors.vector(2)).get(), RealScalar.ZERO);
    assertEquals(VectorAngle.of(Tensors.vector(1), Tensors.vector(-2)).get(), RealScalar.of(Math.PI));
  }

  public void testZero() {
    assertFalse(VectorAngle.of(Tensors.vector(0, 0), Tensors.vector(1, 0)).isPresent());
    assertFalse(VectorAngle.of(Tensors.vector(0.0, 0.0), Tensors.vector(1.0, 0.0)).isPresent());
    assertFalse(VectorAngle.of(Tensors.vector(1.0, 1.0), Tensors.vector(0.0, 0.0)).isPresent());
    assertFalse(VectorAngle.of(Tensors.vector(0, 0), Tensors.vector(0, 0)).isPresent());
  }
}
