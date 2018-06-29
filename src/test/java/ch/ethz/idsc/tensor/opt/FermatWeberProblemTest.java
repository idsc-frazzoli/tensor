// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class FermatWeberProblemTest extends TestCase {
  public void testAxisX() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(2, 0) //
    );
    FermatWeberProblem fwp = new FermatWeberProblem(tensor);
    fwp.setTolerance(0);
    Tensor sol = fwp.weiszfeld();
    assertEquals(sol, Tensors.vector(0, 0));
    assertTrue(fwp.getIterationCount() < 15);
  }

  public void testMedian() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(1, 0) //
    );
    FermatWeberProblem fwp = new FermatWeberProblem(tensor);
    fwp.setTolerance(0);
    Tensor point = Tensors.vector(0.8, 0);
    fwp.setPoint(point);
    Tensor sol = fwp.weiszfeld();
    assertEquals(sol, point);
    assertEquals(fwp.getIterationCount(), 1);
  }

  public void testPoles() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(2, 10), //
        Tensors.vector(2, -10) //
    );
    FermatWeberProblem fwp = new FermatWeberProblem(tensor);
    fwp.setTolerance(1e-2);
    Tensor sol = fwp.weiszfeld();
    assertTrue(fwp.getIterationCount() < 15);
    assertTrue(Norm._2.between(sol, tensor.get(1)).Get().number().doubleValue() < 2e-2);
  }

  public void testWeighted() {
    Tensor tensor = Tensors.of( //
        Tensors.vector(-1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(2, 10), //
        Tensors.vector(2, -10) //
    );
    FermatWeberProblem fwp = new FermatWeberProblem(tensor);
    fwp.setTolerance(1e-10);
    fwp.setWeights(Tensors.vector(10, 1, 1, 1));
    Tensor sol = fwp.weiszfeld();
    assertTrue(fwp.getIterationCount() < 15);
    assertTrue(Norm._2.between(sol, tensor.get(0)).Get().number().doubleValue() < 2e-2);
  }
}
