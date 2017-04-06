// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class SimplexTest extends TestCase {
  /** Quote from "On the Uniqueness of Solutions to Linear Programs" by G. Appa:
   * 
   * four variables actually take value zero at this basis, there are
   * 4C2 = 6 possible simplex tableaux representing the same extra LPP.
   * unique feasible solution, viz x2 = 1 and xj = 0 for j != 2.
   * 
   * It is obvious that because Cj = 0 for all j,
   * the reduced costs in any basic solution to this problem are always zero,
   * and every feasible solution is optimal */
  public void testUnique() {
    Tensor x = LinearProgramming.maxLessEquals( //
        Array.zeros(2), //
        Tensors.fromString("[[3, -1], [-3, 2], [1,-1]]"), //
        Tensors.vectorInt(-1, 2, -1));
    // System.out.println(x);
    assertEquals(x, Tensors.fromString("[0,1]"));
  }

  public void testUnique2() {
    Tensor A = Tensors.fromString("[[3, -1], [-3, 2], [1,-1]]");
    Tensor B = Join.of(1, A, IdentityMatrix.of(3));
    // System.out.println(Pretty.of(B));
    Tensor x = LinearProgramming.minEquals( //
        Array.zeros(5), //
        B, //
        Tensors.vectorInt(-1, 2, -1));
    // System.out.println(x);
    assertEquals(x, Tensors.fromString("[0, 1, 0, 0, 0]"));
  }

  public void testVoid() {
    // ---
  }
}
