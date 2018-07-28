// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Dot;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import junit.framework.TestCase;

public class LieAlgebrasTest extends TestCase {
  public void testHeisenberg() {
    Tensor he3 = LieAlgebras.heisenberg3();
    Tensor eye = IdentityMatrix.of(3);
    assertEquals(Dot.of(he3, eye.get(0), eye.get(1)), eye.get(2));
    assertEquals(Dot.of(he3, eye.get(1), eye.get(0)), eye.get(2).negate());
    assertEquals(JacobiIdentity.of(he3), Array.zeros(3, 3, 3, 3));
  }

  public void testSe2Matrix() {
    Tensor bx = Tensors.fromString("{{0,0,1},{0,0,0},{0,0,0}}");
    Tensor by = Tensors.fromString("{{0,0,0},{0,0,1},{0,0,0}}");
    Tensor bt = Tensors.fromString("{{0,-1,0},{1,0,0},{0,0,0}}");
    assertEquals(LieAlgebras.bracketMatrix(bx, by), Array.zeros(3, 3));
    assertEquals(LieAlgebras.bracketMatrix(bt, bx), by);
    assertEquals(LieAlgebras.bracketMatrix(by, bt), bx);
  }

  public void testSe2() {
    Tensor se2 = LieAlgebras.se2().unmodifiable();
    assertEquals(JacobiIdentity.of(se2), Array.zeros(3, 3, 3, 3));
    assertEquals(se2.dot(UnitVector.of(3, 0)).dot(UnitVector.of(3, 1)), Array.zeros(3));
    assertEquals(se2.dot(UnitVector.of(3, 0)).dot(UnitVector.of(3, 2)), UnitVector.of(3, 1).negate());
    assertEquals(se2.dot(UnitVector.of(3, 1)).dot(UnitVector.of(3, 2)), UnitVector.of(3, 0).negate());
    assertEquals(KillingForm.of(se2), DiagonalMatrix.of(0, 0, 2));
  }

  public void testBracket() {
    Tensor s = LieAlgebras.bracketMatrix(LieAlgebras.so3().get(0), LieAlgebras.so3().get(1));
    assertEquals(s, LieAlgebras.so3().get(2));
  }

  @Deprecated
  public static Tensor from(Tensor c) {
    // System.out.println(Tensor.of(a.flatten(1)).dimensions());
    Tensor a = TensorMap.of(t -> Tensor.of(t.flatten(1)), c, 1);
    System.out.println(Dimensions.of(a));
    System.out.println(a);
    System.out.println(Pretty.of(a));
    Tensor s = a.dot(Transpose.of(a));
    System.out.println(Pretty.of(s));
    List<Tensor> list = new ArrayList<>();
    for (int i = 0; i < c.length() - 1; ++i)
      for (int j = i + 1; j < c.length(); ++j) {
        Tensor d = LieAlgebras.bracketMatrix(c.get(i), c.get(j));
        list.add(Tensor.of(d.flatten(1)));
      }
    Tensor rhs = a.dot(Transpose.of(Tensor.of(list.stream())));
    Tensor sol = LinearSolve.of(s, rhs);
    System.out.println(Pretty.of(sol));
    return null;
  }
}
