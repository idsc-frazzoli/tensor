// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.MapThread;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class LinearProgrammingTest extends TestCase {
  public void testCase4() {
    Tensor c = Tensors.fromString("[-3,-5,0,0,0]");
    Tensor m = Tensors.fromString("[[1,5,1,0,0],[2,1,0,1,0],[1,1,0,0,1]]");
    Tensor b = Tensors.fromString("[40,20,12]");
    Tensor x = LinearProgramming.minEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[5, 7, 0, 3, 0]"));
  }

  public void testCase4max() {
    Tensor c = Tensors.fromString("[3,5,0,0,0]");
    Tensor m = Tensors.fromString("[[1,5,1,0,0],[2,1,0,1,0],[1,1,0,0,1]]");
    Tensor b = Tensors.fromString("[40,20,12]");
    Tensor x = LinearProgramming.maxEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[5, 7, 0, 3, 0]"));
  }

  // MATLAB linprog example
  public void testMatlab1() {
    Tensor c = Tensors.fromString("[-1,-1/3]");
    Tensor m = Tensors.fromString("[[1,1],[1,1/4],[1,-1],[-1/4,-1],[-1,-1],[-1,1]]");
    Tensor b = Tensors.fromString("[2,1,2,1,-1,2]");
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[2/3,4/3]"));
  }

  // MATLAB linprog example
  public void testMatlab2() {
    Tensor c = Tensors.fromString("[-1,-1/3,0,0,0,0,0,0]");
    Tensor Ap = Tensors.fromString("[[1,1],[1,1/4],[1,-1],[-1/4,-1],[-1,-1],[-1,1]]");
    Tensor m = MapThread.of(Join::of, Arrays.asList(Ap, IdentityMatrix.of(6)), 1);
    m.append(Tensors.fromString("[1, 1/4,0,0,0,0,0,0]"));
    Tensor b = Tensors.fromString("[2,1,2,1,-1,2,1/2]");
    Tensor x = LinearProgramming.minEquals(c, m, b);
    assertEquals(x.extract(0, 2), Tensors.fromString("[0,2]"));
  }

  public void testClrsP846() {
    Tensor c = Tensors.fromString("[-1,-1]");
    Tensor m = Tensors.fromString("[[4,-1],[2,1],[-5,2]]");
    Tensor b = Tensors.fromString("[8,10,2]");
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[2,6]")); // see page 847
    assertTrue(LinearProgramming.isFeasible(m, Array.zeros(2), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.fromString("[3,4]"), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.fromString("[1,3]"), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.fromString("[2,0]"), b));
    assertFalse(LinearProgramming.isFeasible(m, Tensors.fromString("[3,3]"), b));
  }

  // same as p846 except that (0,0) is not feasible
  public void testClrsP846var() {
    Tensor c = Tensors.fromString("[-1,-1]");
    Tensor m = Tensors.fromString("[[4,-1],[2,1],[-5,2],[-1,-1]]");
    Tensor b = Tensors.fromString("[8,10,2,-1]");
    Tensor x = LinearProgramming.minLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[2,6]")); // see page 847
    assertFalse(LinearProgramming.isFeasible(m, Array.zeros(2), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.fromString("[3,4]"), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.fromString("[1,3]"), b));
    assertTrue(LinearProgramming.isFeasible(m, Tensors.fromString("[2,0]"), b));
    assertFalse(LinearProgramming.isFeasible(m, Tensors.fromString("[3,3]"), b));
  }

  // infeasible
  public void testClrsP858_6() {
    Tensor c = Tensors.fromString("[-3,2]");
    Tensor m = Tensors.fromString("[[1,1],[-2,-2]]");
    Tensor b = Tensors.fromString("[2,-10]");
    try {
      LinearProgramming.minLessEquals(c, m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  // unbounded
  public void testClrsP858_7() {
    Tensor c = Tensors.fromString("[-1,1]");
    Tensor m = Tensors.fromString("[[-2,1],[-1,-2]]");
    Tensor b = Tensors.fromString("[-1,-2]");
    try {
      LinearProgramming.minLessEquals(c, m, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testClrsP879_5() {
    Tensor c = Tensors.fromString("[18,12.5]");
    Tensor m = Tensors.fromString("[[1, 1],[1,0],[0,1]]");
    Tensor b = Tensors.fromString("[20,12,16]");
    Tensor x = LinearProgramming.maxLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[12,8]")); // confirmed with linprog
  }

  public void testClrsP879_6() {
    Tensor c = Tensors.fromString("[5,-3]");
    Tensor m = Tensors.fromString("[[1, -1],[2,1]]");
    Tensor b = Tensors.fromString("[1,2]");
    Tensor x = LinearProgramming.maxLessEquals(c, m, b);
    assertEquals(x, Tensors.fromString("[1,0]")); // confirmed with linprog
  }

  public void testClrsP879_7() {
    Tensor c = Tensors.fromString("[1,1,1]");
    Tensor m = Tensors.fromString("[[-2, -7.5, -3],[-20, -5, -10]]");
    Tensor b = Tensors.fromString("[-10000,-30000]");
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
  
  public void testKlee() {
    int n = 5;
    KleeMintyCube km = new KleeMintyCube(n);
    km.show();
    Tensor x = LinearProgramming.maxLessEquals(km.c, km.m, km.b);
    System.out.println("x=" + x);
    // System.out.println(km.m.dot(x));
    System.out.println(Power.of(RealScalar.of(5), n));
  }

}
