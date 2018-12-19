// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.qty.Boole;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class LanczosKernelTest extends TestCase {
  public void testSimple() {
    LanczosKernel lanczosKernel = LanczosKernel._3;
    assertEquals(lanczosKernel.semi, 3);
    for (Tensor _x : Range.of(-5, 5 + 1)) {
      Scalar param = _x.Get();
      Scalar scalar = lanczosKernel.apply(param);
      scalar = Chop._12.apply(scalar);
      assertEquals(Boole.of(Scalars.isZero(param)), scalar);
    }
  }

  public void testIntermediate() {
    LanczosKernel lanczosKernel = LanczosKernel._3;
    Scalar apply = lanczosKernel.apply(RationalScalar.HALF);
    Chop._14.requireClose(apply, RealScalar.of(0.6079271018540267));
  }
}
