// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.Inverse;
import junit.framework.TestCase;

public class SpeedTest extends TestCase {
  public long runDot() {
    Tensor r = Tensors.matrixDouble(new double[300][250]);
    Tensor d = Tensors.matrixDouble(new double[250][100]);
    long tic = System.nanoTime();
    r.dot(d);
    return System.nanoTime() - tic;
  }

  public long runAdd() {
    Tensor r = Tensors.matrixDouble(new double[1000][10000]);
    Tensor d = Tensors.matrixDouble(new double[1000][10000]);
    long tic = System.nanoTime();
    r.add(d);
    return System.nanoTime() - tic;
  }

  public long runInverse() {
    Tensor m = IdentityMatrix.of(200);
    long tic = System.nanoTime();
    Inverse.of(m);
    return System.nanoTime() - tic;
  }

  public void testSpeed() {
    // runDot();
    // System.out.println("add " + runAdd() * 1e-9);
    // System.out.println("dot " + runDot() * 1e-9);
    // System.out.println("inv " + runInverse() * 1e-9);
  }
}
