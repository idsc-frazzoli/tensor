// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrettyTest extends TestCase {
  public void testVector() {
    String s = Pretty.of(Tensors.vector(10, 2, 3));
    assertEquals(s, "[ 10   2   3 ]");
  }

  public void testMatrix() {
    String s = Pretty.of(Tensors.matrixInt(new int[][] { { 10, 2, 3 }, { -1, 1, 9 } }));
    assertEquals(s, "[\n [ 10   2   3 ]\n [ -1   1   9 ]\n]");
  }

  public void testNonArray() {
    String s = Pretty.of(Tensors.fromString("{1,2,{3}}"));
    assertEquals(s, "[\n 1  2  [ 3 ]\n]");
  }

  public void testNonArrayNested() {
    String s = Pretty.of(Tensors.fromString("{1,2,{3,{4}}}"));
    assertEquals(s, "[\n 1  2  [\n 3   [ 4 ]\n ]\n]");
  }
}
