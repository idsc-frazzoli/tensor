// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class VectorAngleTest extends TestCase {
  public void testReal() {
    Tensor id = IdentityMatrix.of(5);
    Scalar s1 = VectorAngle.of(id.get(2).multiply(RealScalar.of(3)), id.get(4));
    assertEquals(s1.toString(), "1.5707963267948966");
    Scalar s2 = VectorAngle.of(id.get(2), id.get(2));
    assertEquals(s2, ZeroScalar.get());
  }

  public void testComplex() {
    Tensor u = Tensors.fromString("[1*I,3/4-1*I]");
    Tensor v = Tensors.fromString("[1+1*I,-1/2+2*I]");
    Scalar s1 = VectorAngle.of(u, v);
    assertEquals(s1.toString(), "2.1371816924169798");
  }
}
