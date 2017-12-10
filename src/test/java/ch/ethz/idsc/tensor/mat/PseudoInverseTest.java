// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class PseudoInverseTest extends TestCase {
  public void testMathematica1() {
    Tensor matrix = Tensors.fromString("{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}");
    Tensor result = PseudoInverse.of(matrix);
    Tensor actual = Tensors.fromString("{{-(29/60), -(11/45), -(1/180), 7/30}, {-(1/30), -(1/90), 1/90, 1/30}, {5/12, 2/9, 1/36, -(1/6)}}");
    assertEquals(Chop._12.of(result.subtract(actual)), Array.zeros(3, 4));
  }

  public void testMathematica2() {
    Tensor matrix = Transpose.of(Tensors.fromString("{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}"));
    Tensor result = PseudoInverse.of(matrix);
    Tensor actual = Transpose.of(Tensors.fromString("{{-(29/60), -(11/45), -(1/180), 7/30}, {-(1/30), -(1/90), 1/90, 1/30}, {5/12, 2/9, 1/36, -(1/6)}}"));
    assertEquals(Chop._12.of(result.subtract(actual)), Array.zeros(4, 3));
  }
}
