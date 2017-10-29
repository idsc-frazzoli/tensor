// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LinearProgrammingQuantityTest extends TestCase {
  // MATLAB linprog example
  public void testLinProgMatlab1() { // min c.x == -10/9
    Tensor c = Tensors.fromString("{-1,-1/3}");
    Tensor m = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor b = Tensors.fromString("{2[m], 1[m], 2[m], 1[m], -1[m], 2[m]}");
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    // System.out.println(x); // {2/3[m], 4/3[m]}
    assertEquals(x, Tensors.fromString("{2/3[m],4/3[m]}"));
    // System.out.println(c.dot(x));
  }

  // MATLAB linprog example
  public void testMatlab1() { // min c.x == -10/9
    Tensor c = Tensors.fromString("{-1[m],-1/3[s]}");
    Tensor m = Tensors.fromString("{{1[m],1[s]},{1[m],1/4[s]},{1[m],-1[s]},{-1/4[m],-1[s]},{-1[m],-1[s]},{-1[m],1[s]}}");
    Tensor b = Tensors.vector(2, 1, 2, 1, -1, 2);
    // @SuppressWarnings("unused")
    LinearProgramming.minLessEquals(c, m, b);
    // System.out.println(x);
    // assertEquals(x, Tensors.fromString("{2/3,4/3}"));
    // System.out.println(c.dot(x));
  }
}
