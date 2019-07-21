// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import junit.framework.TestCase;

public class DifferencesTest extends TestCase {
  public void testVector() {
    Tensor dif = Differences.of(Tensors.vector(3, 2, 9).unmodifiable());
    assertEquals(dif, Tensors.vector(-1, 7));
    dif.set(Scalar::zero, Tensor.ALL);
    assertEquals(dif, Tensors.vector(0, 0));
  }

  public void testMatrix1() {
    Tensor dif = Differences.of(Tensors.of(Tensors.vector(3, 2, 9)));
    assertEquals(dif, Tensors.empty());
  }

  public void testMatrix2() {
    Tensor dif = Differences.of(Tensors.of( //
        Tensors.vector(3, 2, 9), //
        Tensors.vector(9, 3, 1) //
    ).unmodifiable());
    assertEquals(dif, Tensors.fromString("{{6, 1, -8}}"));
    dif.set(row -> row.append(RealScalar.of(3)), 0);
    assertEquals(dif.get(0), Tensors.vector(6, 1, -8, 3));
  }

  public void testAd() {
    Tensor dif = Differences.of(LieAlgebras.so3());
    assertEquals(Dimensions.of(dif), Arrays.asList(2, 3, 3));
  }

  public void testConsistent() {
    assertEquals(Differences.of(Tensors.empty()), Tensors.empty());
    assertEquals(Differences.of(Tensors.vector(1)), Tensors.empty());
  }
}
