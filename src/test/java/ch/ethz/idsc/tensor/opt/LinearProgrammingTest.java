// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

// tests should be improved over time
public class LinearProgrammingTest extends TestCase {
  public void testCase4() {
    Tensor c = Tensors.vector(-3, -5, 0, 0, 0);
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 5, 1, 0, 0 }, { 2, 1, 0, 1, 0 }, { 1, 1, 0, 0, 1 } });
    Tensor b = Tensors.vector(40, 20, 12);
    Tensor x = LinearProgramming.minEquals(c, m, b);
    // Mathematica {5, 7, 0, 3, 0}
    assertEquals(x, Tensors.vector(5, 7, 0, 3, 0));
  }

  public void testCase4max() {
    Tensor c = Tensors.vector(3, 5, 0, 0, 0);
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 5, 1, 0, 0 }, { 2, 1, 0, 1, 0 }, { 1, 1, 0, 0, 1 } });
    Tensor b = Tensors.vector(40, 20, 12);
    Tensor x = LinearProgramming.maxEquals(c, m, b);
    assertEquals(x, Tensors.vector(5, 7, 0, 3, 0));
  }

  // MATLAB linprog example
  public void testMatlab1() { // min c.x == -10/9
    Tensor c = Tensors.fromString("{-1,-1/3}");
    Tensor m = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor b = Tensors.vector(2, 1, 2, 1, -1, 2);
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("{2/3,4/3}"));
    // System.out.println(c.dot(x));
  }

  // MATLAB linprog example
  public void testMatlab1max() { // max c.x == min -c.x == -10/9
    Tensor c = Tensors.fromString("{1,1/3}");
    Tensor m = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor b = Tensors.vector(2, 1, 2, 1, -1, 2);
    Tensor x = LinearProgramming.maxLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("{2/3,4/3}"));
    // System.out.println(c.dot(x).negate());
  }

  // MATLAB linprog example dual
  public void testMatlab1maxDual() {
    Tensor c = Tensors.vector(2, 1, 2, 1, -1, 2);
    Tensor m = Transpose.of(Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}"));
    Tensor b = Tensors.fromString("{1,1/3}");
    TensorRuntimeException.of(c, m, b);
    // Tensor y = LinearProgramming.minLessEquals(c, m.negate(), b.negate());
    // System.out.println(y);
    // System.out.println(c.dot(y));
    // assertEquals(x, Tensors.fromString("[2/3,4/3]"));
    // System.out.println(x);
    // System.out.println(c.dot(x));
  }

  // MATLAB linprog example
  public void testMatlab2() {
    Tensor c = Tensors.fromString("{-1,-1/3,0,0,0,0,0,0}");
    Tensor Ap = Tensors.fromString("{{1,1},{1,1/4},{1,-1},{-1/4,-1},{-1,-1},{-1,1}}");
    Tensor m = Join.of(1, Ap, IdentityMatrix.of(6));
    m.append(Tensors.fromString("{1, 1/4,0,0,0,0,0,0}"));
    Tensor b = Tensors.fromString("{2,1,2,1,-1,2,1/2}");
    Tensor x = LinearProgramming.minEquals(c, m, b);
    assertEquals(x.extract(0, 2), Tensors.vector(0, 2));
  }

  public void testClrsP846() { // max cost = 8
    Tensor c = Tensors.vector(1, 1);
    Tensor m = Tensors.matrixInt(new int[][] { { 4, -1 }, { 2, 1 }, { -5, 2 } });
    Tensor b = Tensors.vector(8, 10, 2);
    Tensor x = LinearProgramming.maxLessEquals(c, m, b);
    assertEquals(x, Tensors.vector(2, 6)); // see page 847
    assertTrue(LinearProgramming.isFeasible(m, Array.zeros(2), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.vector(3, 4), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.vector(1, 3), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.vector(2, 0), b));
    assertFalse(LinearProgramming.isFeasible(m, Tensors.vector(3, 3), b));
  }

  public void testClrsP846Dual() {
    Tensor c = Tensors.vector(8, 10, 2);
    Tensor m = Transpose.of(Tensors.matrixInt(new int[][] { { 4, -1 }, { 2, 1 }, { -5, 2 } })).negate();
    Tensor b = Tensors.vector(1, 1).negate();
    TensorRuntimeException.of(c, m, b);
    // Tensor x = LinearProgramming.minLessEquals(c, m, b);
    // System.out.println(x);
    // System.out.println("cost "+c.dot(x));
  }

  // same as p846 except that (0,0) is not feasible
  public void testClrsP846var() {
    Tensor c = Tensors.vector(-1, -1);
    Tensor m = Tensors.matrixInt(new int[][] { { 4, -1 }, { 2, 1 }, { -5, 2 }, { -1, -1 } });
    Tensor b = Tensors.vector(8, 10, 2, -1);
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    // Mathematica {2, 6}
    assertEquals(x, Tensors.vector(2, 6)); // see page 847
    assertFalse(LinearProgramming.isFeasible(m, Array.zeros(2), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.vector(3, 4), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.vector(1, 3), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.vector(2, 0), b));
    assertFalse(LinearProgramming.isFeasible(m, Tensors.vector(3, 3), b));
  }

  // infeasible
  public void testClrsP858_6() {
    Tensor c = Tensors.vector(-3, 2);
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 1 }, { -2, -2 } });
    Tensor b = Tensors.vector(2, -10);
    try {
      LinearProgramming.minLessEquals(c, m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  // unbounded
  public void testClrsP858_7() {
    Tensor c = Tensors.vector(-1, 1);
    Tensor m = Tensors.matrixInt(new int[][] { { -2, 1 }, { -1, -2 } });
    Tensor b = Tensors.vector(-1, -2);
    try {
      LinearProgramming.minLessEquals(c, m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testClrsP879_5() {
    Tensor c = Tensors.vector(18, 12.5);
    Tensor m = Tensors.matrixInt(new int[][] { { 1, 1 }, { 1, 0 }, { 0, 1 } });
    Tensor b = Tensors.vector(20, 12, 16);
    Tensor x = LinearProgramming.maxLessEquals(c, m, b);
    assertEquals(x, Tensors.vector(12, 8)); // confirmed with linprog
  }

  public void testClrsP879_5Dual() {
    Tensor c = Tensors.vector(20, 12, 16);
    Tensor m = Transpose.of(Tensors.matrixInt(new int[][] { { 1, 1 }, { 1, 0 }, { 0, 1 } })).negate();
    Tensor b = Tensors.vector(18, 12.5).negate();
    TensorRuntimeException.of(c, m, b);
    // System.out.println(Pretty.of(m));
    // System.out.println(Pretty.of(b));
    // Tensor x = LinearProgramming.minLessEquals(c, m, b);
    // assertEquals(x, Tensors.fromString("[12,8]")); // confirmed with linprog
    // System.out.println(x);
  }

  public void testClrsP879_6() {
    Tensor c = Tensors.vector(5, -3);
    Tensor m = Tensors.matrixInt(new int[][] { { 1, -1 }, { 2, 1 } });
    Tensor b = Tensors.vector(1, 2);
    Tensor x = LinearProgramming.maxLessEquals(c, m, b);
    assertEquals(x, Tensors.vector(1, 0)); // confirmed with linprog
  }

  public void testClrsP879_7() {
    Tensor c = Tensors.vector(1, 1, 1);
    Tensor m = Tensors.fromString("{{-2, -7.5, -3},{-20, -5, -10}}");
    Tensor b = Tensors.vector(-10000, -30000);
    try {
      LinearProgramming.minLessEquals(c, m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // MATLAB
      // A=[[-2, -7.5, -3];[-20, -5, -10]];
      // b=[-10000;-30000]
      // c=[1,1,1];
      // linprog(c,A,b)
      // ...
      // Exiting: One or more of the residuals, duality gap, or total relative error
      // has grown 100000 times greater than its minimum value so far:
      // the dual appears to be infeasible (and the primal unbounded).
      // (The primal residual < OptimalityTolerance=1.00e-08.)
      // ans =
      // 1.0e+32 *
      // 2.0744
      // 1.3829
      // -4.8403
    }
  }

  public void callKlee(int n) {
    KleeMintyCube kmc = new KleeMintyCube(n);
    Tensor x = LinearProgramming.maxLessEquals(kmc.c, kmc.m, kmc.b);
    assertEquals(x, kmc.x);
  }

  // numeric test
  public void callKleeN(int n) {
    KleeMintyCube kmc = new KleeMintyCube(n);
    Tensor x = LinearProgramming.maxLessEquals(N.DOUBLE.of(kmc.c), N.DOUBLE.of(kmc.m), N.DOUBLE.of(kmc.b));
    assertEquals(x, kmc.x);
  }

  public void testKleeMinty() {
    for (int n = 1; n <= 5; ++n) {
      callKlee(n);
      callKleeN(n);
    }
  }
}
