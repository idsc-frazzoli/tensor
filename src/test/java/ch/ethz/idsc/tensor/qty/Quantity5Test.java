// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.MatrixExp;
import ch.ethz.idsc.tensor.opt.ConvexHull;
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import ch.ethz.idsc.tensor.opt.LinearProgramming;
import ch.ethz.idsc.tensor.red.Quantile;
import junit.framework.TestCase;

public class Quantity5Test extends TestCase {
  // MATLAB linprog example
  public void testLinProgMatlab1() { // min c.x == -10/9
    Tensor c = Tensors.fromString("{-1,-1/3}", Quantity::fromString);
    Tensor m = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor b = Tensors.fromString("{2[m], 1[m], 2[m], 1[m], -1[m], 2[m]}", Quantity::fromString);
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    // System.out.println(x); // {2/3[m], 4/3[m]}
    assertEquals(x, Tensors.fromString("{2/3[m],4/3[m]}", Quantity::fromString));
    // System.out.println(c.dot(x));
  }

  // MATLAB linprog example
  public void testMatlab1() { // min c.x == -10/9
    Tensor c = Tensors.fromString("{-1[m],-1/3[s]}", //
        Quantity::fromString);
    Tensor m = Tensors.fromString("{{1[m],1[s]},{1[m],1/4[s]},{1[m],-1[s]},{-1/4[m],-1[s]},{-1[m],-1[s]},{-1[m],1[s]}}", //
        Quantity::fromString);
    Tensor b = Tensors.vector(2, 1, 2, 1, -1, 2);
    // @SuppressWarnings("unused")
    LinearProgramming.minLessEquals(c, m, b);
    // System.out.println(x);
    // assertEquals(x, Tensors.fromString("{2/3,4/3}"));
    // System.out.println(c.dot(x));
  }

  public void testMatrixExp2() {
    // Mathematica can't do this :-)
    Scalar qs1 = Quantity.of(3, "m");
    Tensor ve1 = Tensors.of(RealScalar.ZERO, qs1);
    Tensor ve2 = Tensors.vector(0, 0);
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor sol = MatrixExp.of(mat);
    assertEquals(sol, mat.add(IdentityMatrix.of(2)));
  }

  public void testMatrixExp3() {
    Scalar qs1 = Quantity.of(2, "m");
    Scalar qs2 = Quantity.of(3, "s");
    Scalar qs3 = Quantity.of(4, "m");
    Scalar qs4 = Quantity.of(5, "s");
    Tensor mat = Tensors.of( //
        Tensors.of(RealScalar.ZERO, qs1, qs3.multiply(qs4)), //
        Tensors.of(RealScalar.ZERO, RealScalar.ZERO, qs2), //
        Tensors.of(RealScalar.ZERO, RealScalar.ZERO, RealScalar.ZERO) //
    );
    Tensor actual = IdentityMatrix.of(3).add(mat).add(mat.dot(mat).multiply(RationalScalar.of(1, 2)));
    assertEquals(MatrixExp.of(mat), actual);
  }

  public void testConvexHull() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(2, "m");
    Scalar qs4 = Quantity.of(-3, "m");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve2, ve1);
    Tensor hul = ConvexHull.of(mat);
    assertEquals(hul, mat);
  }

  public void testQuantile() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(2, "m");
    Tensor vector = Tensors.of(qs1, qs2, qs3);
    assertEquals(Quantile.of(vector, RealScalar.ZERO), qs1);
    assertEquals(Quantile.of(vector, RealScalar.ONE), qs2);
    Scalar qs4 = Quantity.of(2, "s");
    try {
      Sort.of(Tensors.of(qs1, qs4)); // comparison fails
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInterpolation() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(2, "m");
    Tensor vector = Tensors.of(qs1, qs2, qs3);
    Interpolation interpolation = LinearInterpolation.of(vector);
    Scalar r = Quantity.of((1 + 4) * 0.5, "m");
    Scalar s = interpolation.Get(Tensors.vector(0.5));
    assertEquals(s, r);
  }

  public void testInterpolation2() {
    Tensor v1;
    {
      Scalar qs1 = Quantity.of(1, "m");
      Scalar qs2 = Quantity.of(4, "m");
      Scalar qs3 = Quantity.of(2, "m");
      v1 = Tensors.of(qs1, qs2, qs3);
    }
    Tensor v2;
    {
      Scalar qs1 = Quantity.of(9, "s");
      Scalar qs2 = Quantity.of(6, "s");
      Scalar qs3 = Quantity.of(-3, "s");
      v2 = Tensors.of(qs1, qs2, qs3);
    }
    Tensor matrix = Transpose.of(Tensors.of(v1, v2));
    Interpolation interpolation = LinearInterpolation.of(matrix);
    Scalar r1 = Quantity.of((1 + 4) * 0.5, "m");
    Scalar r2 = Quantity.of((9 + 6) * 0.5, "s");
    Tensor vec = interpolation.get(Tensors.vector(0.5));
    assertEquals(vec, Tensors.of(r1, r2));
  }
}
