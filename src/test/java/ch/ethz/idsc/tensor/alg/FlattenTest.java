// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class FlattenTest extends TestCase {
  public void testSimple0a() {
    Tensor m = HilbertMatrix.of(5, 4);
    assertEquals(Flatten.of(m, 0), m);
  }

  public void testSimple0b() {
    Tensor m = Tensors.fromString("{{0,1,{2,{3}}},{{4},5}}");
    assertEquals(Flatten.of(m, 0), m);
  }

  public void testSimple1() {
    Tensor ad = LieAlgebras.heisenberg3();
    assertEquals(Flatten.of(ad, 0), ad);
    assertEquals(Dimensions.of(Flatten.of(ad, 1)), Arrays.asList(9, 3));
    assertEquals(Dimensions.of(Flatten.of(ad, 2)), Arrays.asList(27));
    assertEquals(Dimensions.of(Flatten.of(ad)), Arrays.asList(27));
  }

  public void testSimpleMinusOne() {
    assertEquals(Flatten.of(Tensors.fromString("{{0,1,{{2},3}},{4,5}}")), Range.of(0, 6));
  }

  public void testFail() {
    Tensor ad = LieAlgebras.heisenberg3();
    try {
      Flatten.of(ad, 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
