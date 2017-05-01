// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.NavigableMap;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class SimplexCornersTest extends TestCase {
  public void testCase4() {
    Tensor c = Tensors.vector(-3, -5, 0, 0, 0);
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 5, 1, 0, 0 }, { 2, 1, 0, 1, 0 }, { 1, 1, 0, 0, 1 } });
    Tensor b = Tensors.vector(40, 20, 12);
    NavigableMap<Scalar, Tensor> map = SimplexCorners.minEquals(c, m, b, true);
    // System.out.println(map);
    // System.out.println(map.get(RealScalar.of(-50)));
    assertTrue(map.containsKey(RealScalar.of(-50)));
  }

  // MATLAB linprog example
  public void testMatlab1() { // min c.x == -10/9
    Tensor c = Tensors.fromString("{-1,-1/3,0,0,0,0,0,0}");
    Tensor Ap = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor m = Join.of(1, Ap, IdentityMatrix.of(6));
    Tensor b = Tensors.vector(2, 1, 2, 1, -1, 2);
    NavigableMap<Scalar, Tensor> map = SimplexCorners.minEquals(c, m, b, true);
    // 2/3 4/3
    assertTrue(map.containsKey(RationalScalar.of(-10, 9)));
    // SimplexCorners.show(map);
  }

  // MATLAB linprog example
  public void testMatlab1Dual() {
    Tensor c = Tensors.vector(2, 1, 2, 1, -1, 2, 0, 0).negate();
    Tensor Ap = Transpose.of(Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}")); // .negate();
    Tensor m = Join.of(1, Ap, IdentityMatrix.of(2));
    Tensor b = Tensors.fromString("{-1,-1/3}").negate();
    NavigableMap<Scalar, Tensor> map = SimplexCorners.minEquals(c, m, b, false);
    map.clear();
    // assertTrue(map.containsKey(RationalScalar.of(-10, 9)));
    // System.out.println("dual");
    // SimplexCorners.show(map);
  }

  // MATLAB linprog example
  public void testMatlab2() {
    Tensor c = Tensors.fromString("{-1,-1/3,0,0,0,0,0,0}");
    Tensor Ap = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor m = Join.of(1, Ap, IdentityMatrix.of(6));
    m.append(Tensors.fromString("{1, 1/4,0,0,0,0,0,0}"));
    Tensor b = Tensors.fromString("{2,1,2,1,-1,2,1/2}");
    // Tensor x = LinearProgramming.minEquals(c, m, b);
    NavigableMap<Scalar, Tensor> map = SimplexCorners.minEquals(c, m, b, true);
    assertTrue(map.containsKey(RationalScalar.of(-2, 3)));
    // assertEquals(x.extract(0, 2), Tensors.vector(0, 2));
    // System.out.println(map);
  }
}
