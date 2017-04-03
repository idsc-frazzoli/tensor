// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.MapThread;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class LinearProgrammingTest extends TestCase {
  public void testCase4() {
    Tensor A = Tensors.fromString("[[1,5,1,0,0],[2,1,0,1,0],[1,1,0,0,1]]");
    Tensor b = Tensors.fromString("[40,20,12]");
    Tensor c = Tensors.fromString("[-3,-5,0,0,0]");
    Tensor x = LinearProgramming.ofEquals(A, b, c);
    Tensor sol = Tensors.fromString("[5, 7, 0, 3, 0]");
    assertEquals(x, sol);
    // System.out.println(x);
    // System.out.println(x.dot(c));
    // System.out.println(x.dot(c).Get().number().doubleValue());
  }

  // MATLAB linprog example
  public void testMatlab1() {
    Tensor A = Tensors.fromString("[[1,1],[1,1/4],[1,-1],[-1/4,-1],[-1,-1],[-1,1]]");
    Tensor b = Tensors.fromString("[2,1,2,1,-1,2]");
    Tensor c = Tensors.fromString("[-1,-1/3]");
    Tensor x = LinearProgramming.ofLessEquals(A, b, c);
    assertEquals(x, Tensors.fromString("[2/3,4/3]"));
    // System.out.println(x);
    // System.out.println(x.dot(c));
    // System.out.println(x.dot(c).Get().number().doubleValue());
  }

  // MATLAB linprog example
  public void testMatlab2() {
    Tensor Ap = Tensors.fromString("[[1,1],[1,1/4],[1,-1],[-1/4,-1],[-1,-1],[-1,1]]");
    Tensor A = MapThread.of(Join::of, Arrays.asList(Ap, IdentityMatrix.of(6)), 1);
    A.append(Tensors.fromString("[1, 1/4,0,0,0,0,0,0]"));
    Tensor b = Tensors.fromString("[2,1,2,1,-1,2,1/2]");
    Tensor c = Tensors.fromString("[-1,-1/3,0,0,0,0,0,0]");
    Tensor x = LinearProgramming.ofEquals(A, b, c);
    assertEquals(x.extract(0, 2), Tensors.fromString("[0,2]"));
  }

  public void testClrsP846() {
    Tensor A = Tensors.fromString("[[4,-1],[2,1],[-5,2]]");
    Tensor b = Tensors.fromString("[8,10,2]");
    Tensor c = Tensors.fromString("[-1,-1]");
    Tensor x = LinearProgramming.ofLessEquals(A, b, c);
    assertEquals(x, Tensors.fromString("[2,6]")); // see page 847
  }

  // infeasible
  public void testClrsP858_6() {
    Tensor A = Tensors.fromString("[[1,1],[-2,-2]]");
    Tensor b = Tensors.fromString("[2,-10]");
    Tensor c = Tensors.fromString("[-3,2]");
    try {
      LinearProgramming.ofLessEquals(A, b, c);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  // unbounded
  public void testClrsP858_7() {
    Tensor A = Tensors.fromString("[[-2,1],[-1,-2]]");
    Tensor b = Tensors.fromString("[-1,-2]");
    Tensor c = Tensors.fromString("[-1,1]");
    try {
      LinearProgramming.ofLessEquals(A, b, c);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  // not sure...
  public void testClrsP879_7() {
    Tensor A = Tensors.fromString("[[-2, -7.5, -3],[-20, -5, -10]]");
    Tensor b = Tensors.fromString("[-10000,-30000]");
    Tensor c = Tensors.fromString("[1,1,1]");
    try {
      LinearProgramming.ofLessEquals(A, b, c);
      assertTrue(false); // TODO check with linprog
    } catch (Exception exception) {
      // ---
    }
  }
}
